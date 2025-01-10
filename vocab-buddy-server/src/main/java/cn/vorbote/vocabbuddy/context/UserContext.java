package cn.vorbote.vocabbuddy.context;


import cn.vorbote.vocabbuddy.model.proto.User;

/**
 * 用户上下文
 * <p>
 * Created at 15:06, 19 Mar 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
public final class UserContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    /**
     * 从上下文中获取当前用户
     *
     * @return 当前用户
     */
    public static User getCurrentUser() {
        return currentUser.get();
    }

    /**
     * 设置上下文中的用户
     *
     * @param user 用户信息
     * @return 当前上下文
     */
    public static ThreadLocal<User> setCurrentUser(User user) {
        currentUser.set(user);
        return currentUser;
    }

    /**
     * 清空上下文
     *
     * @return 被清空的上下文
     */
    public static ThreadLocal<User> clear() {
        currentUser.remove();
        return currentUser;
    }

}
