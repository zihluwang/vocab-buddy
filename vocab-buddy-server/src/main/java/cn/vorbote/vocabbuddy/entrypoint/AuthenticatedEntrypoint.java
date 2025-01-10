package cn.vorbote.vocabbuddy.entrypoint;

/**
 * 通用资源接入点，需要登录才能访问
 * <p>
 * Created at 09:22, 25 May 2023
 *
 * @author Zihlu WANG
 */
public final class AuthenticatedEntrypoint {

    private AuthenticatedEntrypoint() {
    }

    public static final String LIST_ALL_TAGS = "/tag/list";

    public static final String LIST_WORD_BY_TAG = "/word/list-by-tag/{tagId}";

}
