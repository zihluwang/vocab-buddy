package cn.vorbote.vocabbuddy.entrypoint;

/**
 * 公共访问接入点，无需任何权限即可访问
 * <p>
 * Created at 00:43, 25 May 2023
 *
 * @author Zihlu WANG
 */
public final class PublicEntrypoint {

    private PublicEntrypoint() {}

    public static final String COMMON_ENTRYPOINT_PATTERN = "/public/**";

    public static final String ADMIN_LOGIN = "/auth/admin/login";

    public static final String USER_LOGIN = "/auth/user/login";

    public static final String USER_REGISTER = "/auth/user/register";

    public static final String SUPPORTED_REGIONS = "/public/supported-regions";
}
