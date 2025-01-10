package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.core.utils.SnowFlake;
import cn.vorbote.vocabbuddy.constant.ArchiveFlag;
import cn.vorbote.vocabbuddy.context.AdminContext;
import cn.vorbote.vocabbuddy.mapstruct.TagMapper;
import cn.vorbote.vocabbuddy.mapstruct.WordDescriptionMapper;
import cn.vorbote.vocabbuddy.mapstruct.WordMapper;
import cn.vorbote.vocabbuddy.model.biz.CustomizedPage;
import cn.vorbote.vocabbuddy.model.dto.WordDTO;
import cn.vorbote.vocabbuddy.model.proto.Word;
import cn.vorbote.vocabbuddy.model.proto.WordDescription;
import cn.vorbote.vocabbuddy.model.proto.WordTag;
import cn.vorbote.vocabbuddy.model.vo.WordVO;
import cn.vorbote.vocabbuddy.rep.TagRep;
import cn.vorbote.vocabbuddy.rep.WordDescriptionRep;
import cn.vorbote.vocabbuddy.rep.WordRep;
import cn.vorbote.vocabbuddy.rep.WordTagRep;
import cn.vorbote.vocabbuddy.service.WordService;
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
import java.util.List;

/**
 * WordServiceImpl
 * <p>
 * Created at 14:01, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Slf4j
@Service
public class WordServiceImpl extends ServiceImpl<WordRep, Word> implements WordService {

    private final WordDescriptionRep wordDescriptionRep;

    private final TagRep tagRep;

    private final WordMapper wordMapper;

    private final WordDescriptionMapper wordDescriptionMapper;

    private final TagMapper tagMapper;

    private final SnowFlake wordGuidUtil;

    private final WordTagRep wordTagRep;

    private final SnowFlake wordTagsGuidUtil;

    private final SnowFlake wordDescriptionsGuidUtil;

    @Autowired
    public WordServiceImpl(WordDescriptionRep wordDescriptionRep,
                           TagRep tagRep,
                           WordMapper wordMapper,
                           WordDescriptionMapper wordDescriptionMapper,
                           TagMapper tagMapper,
                           SnowFlake wordGuidUtil,
                           WordTagRep wordTagRep,
                           SnowFlake wordTagsGuidUtil,
                           SnowFlake wordDescriptionsGuidUtil) {
        this.wordDescriptionRep = wordDescriptionRep;
        this.tagRep = tagRep;
        this.wordMapper = wordMapper;
        this.wordDescriptionMapper = wordDescriptionMapper;
        this.tagMapper = tagMapper;
        this.wordGuidUtil = wordGuidUtil;
        this.wordTagRep = wordTagRep;
        this.wordTagsGuidUtil = wordTagsGuidUtil;
        this.wordDescriptionsGuidUtil = wordDescriptionsGuidUtil;
    }

    @Override
    public IPage<WordVO> fetchPaginatedWordsByTagId(Long current, Long size, Long tagId) {
        // 查询总条数
        var total = baseMapper.countWordsByTagId(tagId);
        // 查询指定数量的单词
        var protoWords = baseMapper.fetchPaginatedWordByTagId((current - 1) * size, size, tagId);

        // 创建返回数据
        var resultWords = new ArrayList<WordVO>();

        for (var word : protoWords) {
            // 查询该单词的所有含义
            var wordDescriptions = wordDescriptionRep.selectList(Wrappers.<WordDescription>lambdaQuery()
                            .eq(WordDescription::getWordId, word.getId()))
                    .stream()
                    .map(wordDescriptionMapper::map)
                    .toList();

            // 查询该单词的所有标签
            var tags = tagRep.fetchTagsByWordId(word.getId())
                    .stream()
                    .map(tagMapper::map)
                    .toList();

            // 将单词转换为VO并添加到返回数据中
            resultWords.add(wordMapper.map(word)
                    .setTags(tags)
                    .setWordDescriptions(wordDescriptions));
        }

        // 对每个单词查询
        return new CustomizedPage<WordVO>()
                .setRecords(resultWords)
                .setTotal(total)
                .setCurrent(current)
                .setSize(size);
    }

    @Override
    public Integer countWordsByTagId(Long tagId) {
        return baseMapper.countWordsByTagId(tagId);
    }

    @Override
    public List<Word> fetchWordsByTagId(Long tagId) {
        return baseMapper.fetchWordsByTagId(tagId);
    }

    @Transactional
    @Override
    public List<WordVO> uploadWords(List<WordDTO> userUploadedWords, Long tagId) {
        final var currentAdmin = AdminContext.getCurrentAdmin();

        final var wordVos = new ArrayList<WordVO>();

        final var tag = tagRep.selectById(tagId);
        BizAssert.notNull(tag, "无法查询到相关标签！");

        var wordsCouldBeSaved = userUploadedWords.stream()
                .filter((word) -> !baseMapper.exists(Wrappers.<Word>lambdaQuery()
                        .eq(Word::getWord, word.getWord())))
                .toList();
        for (var wordCouldBeSaved : wordsCouldBeSaved) {
            var word = wordMapper.map(wordCouldBeSaved)
                    .setId(wordGuidUtil.nextId())
                    .setCreateBy(currentAdmin.composeDisplayName())
                    .setCreateAt(LocalDateTime.now())
                    .setArchived(0);

            if (baseMapper.insert(word) > 0) {
                wordTagRep.insert(new WordTag()
                        .setId(wordTagsGuidUtil.nextId())
                        .setWordId(word.getId())
                        .setTagId(tag.getId())
                        .setCreateBy(currentAdmin.composeDisplayName())
                        .setCreateAt(LocalDateTime.now())
                        .setArchived(ArchiveFlag.NOT_ARCHIVED));

                var wordDescriptions = wordCouldBeSaved.getWordDescriptions()
                        .stream()
                        .map(wordDescriptionMapper::map)
                        .map((item) -> item.setId(wordDescriptionsGuidUtil.nextId())
                                .setWordId(word.getId())
                                .setCreateBy(currentAdmin.composeDisplayName())
                                .setCreateAt(LocalDateTime.now())
                                .setArchived(0))
                        .toList();

                if (wordDescriptionRep.storeBatch(wordDescriptions) > 0) {
                    wordVos.add(wordMapper.map(word)
                            .setTags(List.of(tagMapper.map(tag)))
                            .setWordDescriptions(wordDescriptions.stream()
                                    .map(wordDescriptionMapper::map)
                                    .toList()));
                }
            }
        }
        return wordVos;
    }

}
