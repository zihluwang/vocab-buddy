package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.core.constants.Hash;
import cn.vorbote.core.utils.HashUtil;
import cn.vorbote.vocabbuddy.rep.AdminRep;
import cn.vorbote.vocabbuddy.model.proto.Admin;
import cn.vorbote.vocabbuddy.service.AdminService;
import cn.vorbote.web.utils.BizAssert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 管理员业务实现类
 * <p>
 * Created at 16:30, 23 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminRep, Admin> implements AdminService {

    @Override
    public Admin fetchAdmin(String username, String password) {
        // log.info("{}", HashUtil.encrypt(Hash.MD5, "VocabBuddy" + password));
        var admin = baseMapper.fetchAdminByUsername(username);
        BizAssert.notNull(admin, "用户名错误！");
        BizAssert.isTrue(
                HashUtil.encrypt(Hash.MD5, "VocabBuddy" + password).equalsIgnoreCase(admin.getPassword()),
                "密码错误！");
        return admin;
    }

}
