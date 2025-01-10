package cn.vorbote.vocabbuddy.context;

import cn.vorbote.vocabbuddy.model.proto.Admin;
import cn.vorbote.vocabbuddy.model.proto.User;

/**
 * 管理员上下文
 * <p>
 * Created at 15:51, 23 May 2023
 *
 * @author Zihlu WANG
 */
public final class AdminContext {

    private static final ThreadLocal<Admin> currentAdmin = new ThreadLocal<>();

    /**
     * 获取当前管理员
     *
     * @return 当前管理员
     */
    public static Admin getCurrentAdmin() {
        return currentAdmin.get();
    }

    /**
     * 设置当前管理员
     *
     * @param admin 管理员
     * @return 包含管理员数据的上下文
     */
    public static ThreadLocal<Admin> setCurrentAdmin(Admin admin) {
        currentAdmin.set(admin);
        return currentAdmin;
    }

    /**
     * 清空上下文
     *
     * @return 包含管理员数据的上下文
     */
    public static ThreadLocal<Admin> clear() {
        currentAdmin.remove();
        return currentAdmin;
    }

}
