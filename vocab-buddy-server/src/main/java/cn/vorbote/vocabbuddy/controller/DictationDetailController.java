package cn.vorbote.vocabbuddy.controller;

import cn.vorbote.core.utils.SnowFlake;
import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.vocabbuddy.context.UserContext;
import cn.vorbote.vocabbuddy.mapstruct.DictationDetailMapper;
import cn.vorbote.vocabbuddy.model.dto.DictationDetailDTO;
import cn.vorbote.vocabbuddy.service.DictationDetailService;
import cn.vorbote.vocabbuddy.service.DictationService;
import cn.vorbote.vocabbuddy.service.WordService;
import cn.vorbote.web.model.ResponseResult;
import cn.vorbote.web.utils.BizAssert;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * DictationDetailController
 * <p>
 * Created at 18:22, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@RestController
@RequestMapping("/dictation-detail")
public class DictationDetailController {

    private final DictationDetailService dictationDetailService;

    private final DictationService dictationService;

    private final DictationDetailMapper dictationDetailMapper;

    private final WordService wordService;

    private final SnowFlake dictationDetailGuidUtil;

    @Autowired
    public DictationDetailController(DictationDetailService dictationDetailService,
                                     DictationService dictationService,
                                     DictationDetailMapper dictationDetailMapper,
                                     WordService wordService,
                                     SnowFlake dictationDetailGuidUtil) {
        this.dictationDetailService = dictationDetailService;
        this.dictationService = dictationService;
        this.dictationDetailMapper = dictationDetailMapper;
        this.wordService = wordService;
        this.dictationDetailGuidUtil = dictationDetailGuidUtil;
    }

    @PostMapping("/{dictationId}")
    public ResponseResult<Void> recordDictationDetails(@PathVariable Long dictationId,
                                                       @RequestBody DictationDetailDTO dictationDetailDTO) {
        // 获取当前用户
        var user = UserContext.getCurrentUser();

        // 检查数据
        BizAssert.notNull(dictationDetailDTO.getWordId(), "单词ID不能为空！");
        BizAssert.notNull(dictationDetailDTO.getUserSpelling(), "用户的拼写不能为空！");
        BizAssert.isTrue(
                dictationService.checkOwnership(dictationId, user.getId()),
                HttpServletResponse.SC_FORBIDDEN,
                "您没有该听写测试的操作权限！");
        var currentWord = wordService.getById(dictationDetailDTO.getWordId());
        BizAssert.notNull(currentWord, HttpServletResponse.SC_BAD_REQUEST, "当前用户上传了错误的单词！");

        // 转换数据
        var dictationDetail = dictationDetailMapper.map(dictationDetailDTO)
                .setId(dictationDetailGuidUtil.nextId())
                .setDictationId(dictationId)
                .setCorrect(currentWord.getWord().equals(dictationDetailDTO.getUserSpelling()) ? 1 : 0)
                .setCreateAt(LocalDateTime.now())
                .setCreateBy(user.composeDisplayName());

        BizAssert.isTrue(
                dictationDetailService.recordDictationDetail(dictationDetail) == 1,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "因为未知原因保存失败，请联系客服处理！");
        return ResponseResult.<Void>success(null, "听写详情保存成功！")
                .requestId(CommonContext.getRequestId().toString());
    }

}
