package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.vocabbuddy.constant.AnsweringMode;
import cn.vorbote.vocabbuddy.constant.GenerationMode;
import cn.vorbote.vocabbuddy.mapstruct.*;
import cn.vorbote.vocabbuddy.model.biz.CustomizedPage;
import cn.vorbote.vocabbuddy.model.proto.Dictation;
import cn.vorbote.vocabbuddy.model.proto.DictationDetail;
import cn.vorbote.vocabbuddy.model.proto.Word;
import cn.vorbote.vocabbuddy.model.proto.WordDescription;
import cn.vorbote.vocabbuddy.model.vo.DictationVO;
import cn.vorbote.vocabbuddy.model.vo.WordVO;
import cn.vorbote.vocabbuddy.rep.*;
import cn.vorbote.vocabbuddy.service.DictationService;
import cn.vorbote.web.utils.BizAssert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DictationServiceImpl
 * <p>
 * Created at 15:37, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Slf4j
@Service
public class DictationServiceImpl extends ServiceImpl<DictationRep, Dictation> implements DictationService {

    private final DictationMapper dictationMapper;

    private final WordRep wordRep;

    private final WordDescriptionRep wordDescriptionRep;

    private final WordDescriptionMapper wordDescriptionMapper;

    private final WordMapper wordMapper;

    private final TagRep tagRep;

    private final TagMapper tagMapper;

    private final UserRep userRep;

    private final DictationDetailRep dictationDetailRep;

    private final DictationDetailMapper dictationDetailMapper;

    private final DictationRep dictationRep;

    @Autowired
    public DictationServiceImpl(DictationMapper dictationMapper, WordRep wordRep,
                                WordDescriptionRep wordDescriptionRep,
                                WordDescriptionMapper wordDescriptionMapper,
                                WordMapper wordMapper,
                                TagRep tagRep,
                                TagMapper tagMapper,
                                UserRep userRep,
                                DictationDetailRep dictationDetailRep, DictationDetailMapper dictationDetailMapper, DictationRep dictationRep) {
        this.dictationMapper = dictationMapper;
        this.wordRep = wordRep;
        this.wordDescriptionRep = wordDescriptionRep;
        this.wordDescriptionMapper = wordDescriptionMapper;
        this.wordMapper = wordMapper;
        this.tagRep = tagRep;
        this.tagMapper = tagMapper;
        this.userRep = userRep;
        this.dictationDetailRep = dictationDetailRep;
        this.dictationDetailMapper = dictationDetailMapper;
        this.dictationRep = dictationRep;
    }

    @Transactional
    @Override
    public DictationVO generateDictation(Dictation dictation) {
        // 读取生成模式以及答题模式
        var generationMode = dictation.getGenerationMode();
        var answeringMode = dictation.getAnsweringMode();

        // 加载词库
        var wordLib = loadWordsLibrary(
                generationMode,
                dictation.getTagId(),
                dictation.getWordsCount(),
                dictation.getUserId());
        if (answeringMode == AnsweringMode.CHOICE) {
            for (var word : wordLib) {
                var choices = new ArrayList<String>();
                choices.add(word.getWord());
                choices.add(upset(word.getWord()));
                choices.add(upset(word.getWord()));
                choices.add(upset(word.getWord()));

                Collections.shuffle(choices);
                word.setChoices(choices);
            }
        }

        // 保存听写测试
        BizAssert.isTrue(baseMapper.insert(dictation) > 0, "生成测试时出现异常！");

        return dictationMapper.map(dictation).setWordLib(wordLib);
    }

    @Override
    public List<WordVO> loadWordsLibrary(GenerationMode generationMode, Long tagId, Integer libSize, Long userId) {
        // 查询单词
        var wordLib = switch (generationMode) {
            case RANDOM -> {
                var words = wordRep.fetchWordsByTagId(tagId);
                Collections.shuffle(words);
                yield words.subList(0, libSize);
            }
            case INCORRECT -> {
                // 获取最近五次的听写测试ID
                var dictationsIds = dictationRep.fetchLatestDictations(userId);

                // 获取最近五次错误的单词
                var words = dictationDetailRep.fetchIncorrectWords(dictationsIds);
                yield wordRep.fetchWords(words);
            }
        };

        var resultWords = new ArrayList<WordVO>();
        for (var word : wordLib) {
            // 查询单词含义
            var wordDescriptions = wordDescriptionRep.selectList(Wrappers.<WordDescription>lambdaQuery()
                            .eq(WordDescription::getWordId, word.getId())).stream()
                    .map((wordDescriptionMapper::map))
                    .toList();

            // 查询单词标签
            var tags = tagRep.fetchTagsByWordId(word.getId())
                    .stream()
                    .map(tagMapper::map)
                    .toList();

            resultWords.add(wordMapper.map(word)
                    .setWordDescriptions(wordDescriptions)
                    .setTags(tags));
        }

        return resultWords;
    }

    @Override
    public boolean checkOwnership(Long dictationId, Long userId) {
        return baseMapper.selectCount(Wrappers.<Dictation>lambdaQuery()
                .eq(Dictation::getId, dictationId)
                .eq(Dictation::getUserId, userId)) == 1;
    }

    @Override
    public boolean isDictationStopped(Long dictationId) {
        return baseMapper.selectCount(Wrappers.<Dictation>lambdaQuery()
                .eq(Dictation::getId, dictationId)
                .eq(Dictation::getEndTime, null)) == 1;
    }

    @Override
    public DictationVO stopDictation(Long dictationId) {
        final var now = LocalDateTime.now();
        // 获取听写测试
        var dictation = baseMapper.selectById(dictationId);
        // 获取听写测试对应的用户数据
        var user = userRep.selectById(dictation.getUserId());

        // 计算单词数量
        var wordsCount = dictation.getCorrectWordsCount() + dictation.getIncorrectWordsCount();
        // 设置数据
        dictation.setUpdateAt(now)
                .setUpdateBy(user.composeDisplayName())
                .setEndTime(now)
                .setWordsCount(wordsCount);
        BizAssert.isTrue(baseMapper.updateById(dictation) > 0, "听写测试停止失败！");

        return dictationMapper.map(dictation);
    }

    @Override
    public boolean isExist(Long dictationId) {
        return baseMapper.selectCount(Wrappers.<Dictation>lambdaQuery()
                .eq(Dictation::getId, dictationId)) == 1;
    }

    @Override
    public IPage<DictationVO> fetchPaginatedDictations(Long current, Long size, Long userId) {
        // 构建分页数据
        var page = new CustomizedPage<DictationVO>();

        // 设置总条目数量
        var total = baseMapper.countDictationsByUser(userId);
        page.setTotal(total).setSize(size).setCurrent(current);

        if (total > 0) {
            // 查询听写信息详细数据
            var dictations = baseMapper
                    .fetchPaginatedDictation((current - 1) * size, size, userId)
                    .stream()
                    .map(dictationMapper::map)
                    .toList();

            page.setRecords(dictations);
        }
        return page;
    }

    @Override
    public DictationVO fetchOne(Long dictationId) {
        var dictation = dictationMapper.map(baseMapper.selectById(dictationId));

        var dictationDetails = dictationDetailRep.selectList(
                        Wrappers.<DictationDetail>lambdaQuery()
                                .eq(DictationDetail::getDictationId, dictation.getId())
                                .orderByAsc(DictationDetail::getCreateAt))
                .stream()
                .map(dictationDetailMapper::map)
                .map(((dictationDetail) -> dictationDetail
                        .setWord(wordRep.selectById(Long.parseLong(dictationDetail.getWordId())).getWord())
                        .setWordDescriptions(
                                wordDescriptionRep.selectList(Wrappers.<WordDescription>lambdaQuery()
                                                .eq(WordDescription::getWordId, dictationDetail.getWordId()))
                                        .stream()
                                        .map(wordDescriptionMapper::map)
                                        .toList())))
                .toList();

        return dictation.setDictationDetails(dictationDetails);
    }


}
