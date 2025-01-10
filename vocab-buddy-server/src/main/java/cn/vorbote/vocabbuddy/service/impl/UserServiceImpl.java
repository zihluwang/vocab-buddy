package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.core.constants.Hash;
import cn.vorbote.core.utils.HashUtil;
import cn.vorbote.core.utils.SnowFlake;
import cn.vorbote.vocabbuddy.model.proto.User;
import cn.vorbote.vocabbuddy.rep.UserRep;
import cn.vorbote.vocabbuddy.service.UserService;
import cn.vorbote.web.utils.BizAssert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * UserServiceImpl
 * <p>
 * Created at 21:12, 23 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserRep, User> implements UserService {

    private final SnowFlake userGuidUtil;

    @Autowired
    public UserServiceImpl(SnowFlake userGuidUtil) {
        this.userGuidUtil = userGuidUtil;
    }

    @Override
    public User fetchUser(String username, String password) {
        var user = baseMapper.fetchUserByUsername(username);
        BizAssert.notNull(user, "用户名错误！");
        BizAssert.isTrue(
                HashUtil.encrypt(Hash.MD5, "VocabBuddy" + password).equalsIgnoreCase(user.getPassword()),
                "密码错误！");
        return user;
    }

    @Override
    public User register(User user) {
        var encryptedPassword = HashUtil.encrypt(Hash.MD5, "VocabBuddy" + user.getPassword());
        // 设置数据
        user.setId(userGuidUtil.nextId())
                .setCreateAt(LocalDateTime.now())
                .setCreateBy(user.composeDisplayName())
                .setPassword(encryptedPassword);
        // 检查能否注册
        beforeRegister(user);
        // 开始注册
        BizAssert.isTrue(
                baseMapper.insert(user) > 0,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "由于未知原因注册失败，若多次出现该提示请联系客服处理！");
        return user;
    }

    @Override
    public void beforeRegister(User user) {
        // 检查用户名
        BizAssert.isTrue(baseMapper.countByUsername(user.getUsername()) == 0, "用户名已被占用，请重试！");
        // 检查电话号码
        BizAssert.isTrue(baseMapper.countByPhone(user.getRegion().getCode(), user.getPhone()) == 0, "电话号码已被占用，请重试！");
        // 检查电子邮箱
        if (Optional.ofNullable(user.getEmail()).isPresent()) {
            BizAssert.isTrue(baseMapper.countByEmail(user.getEmail()) == 0, "电子邮箱已被占用，请重试！");
        }
    }
}
