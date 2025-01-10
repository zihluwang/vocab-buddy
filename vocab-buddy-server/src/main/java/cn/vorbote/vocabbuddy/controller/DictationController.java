package cn.vorbote.vocabbuddy.controller;

import cn.vorbote.core.utils.SnowFlake;
import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.vocabbuddy.context.UserContext;
import cn.vorbote.vocabbuddy.entrypoint.UserEntrypoint;
import cn.vorbote.vocabbuddy.mapstruct.DictationMapper;
import cn.vorbote.vocabbuddy.mapstruct.WordMapper;
import cn.vorbote.vocabbuddy.model.dto.DictationDTO;
import cn.vorbote.vocabbuddy.model.proto.User;
import cn.vorbote.vocabbuddy.model.vo.DictationVO;
import cn.vorbote.vocabbuddy.service.DictationService;
import cn.vorbote.vocabbuddy.service.WordService;
import cn.vorbote.web.exceptions.BizException;
import cn.vorbote.web.model.ResponseResult;
import cn.vorbote.web.utils.BizAssert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

/**
 * DictationController
 * <p>
 * Created at 15:38, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Slf4j
@RestController
@RequestMapping("/dictation")
public class DictationController {

    private final DictationService dictationService;

    private final WordService wordService;

    private final DictationMapper dictationMapper;

    private final SnowFlake dictationGuidUtil;

    @Autowired
    public DictationController(DictationService dictationService,
                               WordService wordService,
                               DictationMapper dictationMapper,
                               SnowFlake dictationGuidUtil) {
        this.dictationService = dictationService;
        this.wordService = wordService;
        this.dictationMapper = dictationMapper;
        this.dictationGuidUtil = dictationGuidUtil;
    }

    @PostMapping("/generate")
    public ResponseResult<DictationVO> generateDictation(@RequestBody DictationDTO dictationDTO) {
        // 获取用户数据
        var currentUser = UserContext.getCurrentUser();

        // 转换数据
        var dictation = Optional.ofNullable(dictationDTO)
                .map(dictationMapper::map)
                .orElseThrow(() -> new BizException(HttpServletResponse.SC_BAD_REQUEST, "生成参数缺失！"));

        // 提取数据
        final var generationMode = dictation.getGenerationMode();

        // 检查数据，设置数据并加载词库
        switch (generationMode) {
            case RANDOM -> {
                BizAssert.notNull(dictation.getWordsCount(), "听写测试单词数量上限不存在！");
                BizAssert.notNull(dictation.getTagId(), "标签 ID 无效！");
                // 随机模式下，测试单词数量上限应该小于单词总数
                var wordsCountInWordLibrary = wordService.countWordsByTagId(dictation.getTagId());
                BizAssert.isTrue(
                        wordsCountInWordLibrary >= dictation.getWordsCount(),
                        "单词数量（%d）上限超过词库中单词数量（%d）！".formatted(dictation.getWordsCount(), wordsCountInWordLibrary));

                // 设置其他数据

            }
            case INCORRECT -> {
                // 检查数据

                // 设置其他数据
                dictation.setTagId(129725075644489728L); // 该 Tag 是错题专用 Tag
            }
        }

        // 统一需要设置的数据
        dictation.setId(dictationGuidUtil.nextId())
                .setUserId(currentUser.getId())
                .setCreateBy(currentUser.composeDisplayName())
                .setCreateAt(LocalDateTime.now())
                .setCorrectWordsCount(0)
                .setIncorrectWordsCount(0)
                .setStartTime(LocalDateTime.now())
                .setEndTime(null);

        // 让 Service 生成听写
        return ResponseResult.success(dictationService.generateDictation(dictation), "生成听写测试成功！");
    }

    @GetMapping("/{dictationId}/stop")
    public ResponseResult<DictationVO> stopDictation(@PathVariable Long dictationId) {
        var user = UserContext.getCurrentUser();

        // 检查听写测试
        BizAssert.isTrue(dictationService.isExist(dictationId), "听写测试不存在！");
        BizAssert.isTrue(
                dictationService.checkOwnership(dictationId, user.getId()),
                HttpServletResponse.SC_FORBIDDEN,
                "对不起，您没有该听写测试的操作权限！");
        BizAssert.isTrue(!dictationService.isDictationStopped(dictationId), HttpServletResponse.SC_ACCEPTED, "听写测试已结束！");

        return ResponseResult.success(dictationService.stopDictation(dictationId), "听写测试停止成功！");
    }

    @GetMapping("/historical-dictations")
    public ResponseResult<IPage<DictationVO>> retrieveHistoricalDictations(@RequestParam(defaultValue = "1") Long current,
                                                                           @RequestParam(defaultValue = "10") Long size) {
        var currentUser = UserContext.getCurrentUser();
        return ResponseResult
                .success(dictationService.fetchPaginatedDictations(current, size, currentUser.getId()), "查询历史记录成功！")
                .requestId(CommonContext.getRequestId().toString());
    }

    @GetMapping("/{dictationId}")
    public ResponseResult<DictationVO> retrieveDictation(@PathVariable Long dictationId) {
        var currentUser = UserContext.getCurrentUser();

        BizAssert.isTrue(dictationService.isExist(dictationId), "该听写测试不存在！");
        BizAssert.isTrue(
                dictationService.checkOwnership(dictationId, currentUser.getId()),
                HttpServletResponse.SC_FORBIDDEN,
                "您没有该听写测试的操作权限！");
        return ResponseResult.success(dictationService.fetchOne(dictationId))
                .requestId(CommonContext.getRequestId().toString());
    }
}
