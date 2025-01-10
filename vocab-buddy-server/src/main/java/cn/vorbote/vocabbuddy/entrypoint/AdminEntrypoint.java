package cn.vorbote.vocabbuddy.entrypoint;

/**
 * 管理员接入点，需要管理员身份才能访问
 * <p>
 * Created at 00:44, 25 May 2023
 *
 * @author Zihlu WANG
 */
public final class AdminEntrypoint {
    private AdminEntrypoint() {}

    public static final String COMMON_ENTRYPOINT_PATTERN = "/admin/**";
}
