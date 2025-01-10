package cn.vorbote.vocabbuddy.model.web.request;

/**
 * LoginParam
 * <p>
 * Created at 23:15, 23 May 2023
 *
 * @author Zihlu WANG
 */
public record LoginRequest(
        String username,
        String password
) {
}
