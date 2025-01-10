package cn.vorbote.vocabbuddy.controller;

import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.vocabbuddy.model.vo.WordVO;
import cn.vorbote.vocabbuddy.service.ExcelService;
import cn.vorbote.vocabbuddy.service.WordService;
import cn.vorbote.web.model.ResponseResult;
import cn.vorbote.web.utils.BizAssert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * WordController
 * <p>
 * Created at 14:02, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Slf4j
@RestController
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;

    private final ExcelService excelService;

    @Autowired
    public WordController(WordService wordService, ExcelService excelService) {
        this.wordService = wordService;
        this.excelService = excelService;
    }

    @GetMapping("/list-by-tag/{tagId}")
    public ResponseResult<IPage<WordVO>> retrieveWordByTag(@RequestParam(defaultValue = "1") Long current,
                                                           @RequestParam(defaultValue = "10") Long size,
                                                           @PathVariable Long tagId) {
        // 限制 size 大小
        size = (size <= 10 && size > 0) ? size : 10;

        var paginatedWords = wordService.fetchPaginatedWordsByTagId(current, size, tagId);
        return ResponseResult.success(paginatedWords, "查询成功！")
                .requestId(CommonContext.getRequestId().toString());
    }

    @PostMapping("/upload/{tagId}")
    public ResponseResult<List<WordVO>> uploadWords(@PathVariable Long tagId,
                                                    @RequestBody MultipartFile file) throws IOException {
        BizAssert.notNull(file, "无法获取文件！");

        var resolvedData = excelService.resolveMultipartFile(file);
        if (!Objects.isNull(resolvedData)) {
            var uploadedWords = wordService.uploadWords(resolvedData, tagId);
        }

        return null;
    }

}
