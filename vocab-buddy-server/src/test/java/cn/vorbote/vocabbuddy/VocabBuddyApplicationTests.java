package cn.vorbote.vocabbuddy;

import cn.vorbote.core.time.TimeSpan;
import cn.vorbote.core.utils.SnowFlake;
import cn.vorbote.simplejwt.AccessKeyUtil;
import cn.vorbote.vocabbuddy.constant.Region;
import cn.vorbote.vocabbuddy.model.proto.User;
import cn.vorbote.vocabbuddy.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"dev", "local"})
@SpringBootTest
class VocabBuddyApplicationTests {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SnowFlake dictationGuidUtil;

    @Autowired
    private AccessKeyUtil accessKeyUtil;

    @Test
    void contextLoads() throws IllegalAccessException {
        System.out.println(dictationGuidUtil.nextId());
    }

}
