package cn.vorbote.vocabbuddy.controller;

import cn.vorbote.vocabbuddy.constant.Region;
import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.web.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PublicController
 * <p>
 * Created at 21:38, 23 May 2023
 *
 * @author Zihlu WANG
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    private List<Region> supportedRegions;

    @Autowired
    public void setSupportedRegions(List<Region> supportedRegions) {
        this.supportedRegions = supportedRegions;
    }

    @GetMapping("/supported-regions")
    public ResponseResult<List<Region>> retrieveSupportedRegions() {
        return ResponseResult.success(supportedRegions, "数据查询成功！")
                .requestId(CommonContext.getRequestId().toString());
    }
}
