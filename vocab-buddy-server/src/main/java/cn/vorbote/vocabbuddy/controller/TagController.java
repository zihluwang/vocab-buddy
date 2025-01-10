package cn.vorbote.vocabbuddy.controller;

import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.vocabbuddy.mapstruct.TagMapper;
import cn.vorbote.vocabbuddy.model.vo.TagVO;
import cn.vorbote.vocabbuddy.service.TagService;
import cn.vorbote.web.model.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TagController
 * <p>
 * Created at 12:32, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    private final TagMapper tagMapper;

    @Autowired
    public TagController(TagService tagService,
                         TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @GetMapping("/list")
    public ResponseResult<List<TagVO>> retrieveTags() {
        var tags = tagService.fetchAllTags().stream()
                .map(tagMapper::map)
                .toList();
        return ResponseResult.success(tags, "查询成功！")
                .requestId(CommonContext.getRequestId().toString());
    }
}
