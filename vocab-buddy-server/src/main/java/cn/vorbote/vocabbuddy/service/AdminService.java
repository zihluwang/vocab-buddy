package cn.vorbote.vocabbuddy.service;

import cn.vorbote.vocabbuddy.model.proto.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 管理员业务
 * <p>
 * Created at 16:29, 23 May 2023
 *
 * @author Zihlu WANG
 */
public interface AdminService extends IService<Admin> {

    /**
     * 根据用户名和密码获取用户信息
     *
     * @param username 用户名
     * @param password 密码，以明文传递
     * @return 查询到的用户
     * @throws cn.vorbote.web.exceptions.BizException 用户名或密码不正确将会抛出此异常
     */
    Admin fetchAdmin(String username, String password);

}
