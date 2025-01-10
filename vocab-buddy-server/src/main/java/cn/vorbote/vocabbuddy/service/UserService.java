package cn.vorbote.vocabbuddy.service;

import cn.vorbote.vocabbuddy.model.proto.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * UserService
 * <p>
 * Created at 21:11, 23 May 2023
 *
 * @author Zihlu WANG
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名和密码获取用户信息
     *
     * @param username 用户名
     * @param password 密码，以明文传递
     * @return 查询到的用户
     * @throws cn.vorbote.web.exceptions.BizException 用户名或密码不正确将会抛出此异常
     */
    User fetchUser(String username, String password);

    /**
     * 用户注册
     *
     * @param user 被注册的用户
     * @return 注册好的用户
     */
    User register(User user);

    /**
     * 检查用户是否可以注册
     *
     * @param user 被检查的用户
     * @return 如果可以注册，返回 {@code true}；否则返回 {@code false}
     */
    void beforeRegister(User user);

}
