package cn.vorbote.vocabbuddy.context;

import java.util.UUID;

/**
 * 通用上下文
 * <p>
 * Created at 11:44, 28 Mar 2023
 *
 * @author vorbote
 */
public final class CommonContext {

    private static final ThreadLocal<UUID> requestIdContext = new ThreadLocal<>();

    /**
     * 设置请求 ID
     *
     * @param requestId 请求 ID
     * @return 包含请求 ID 的上下文
     */
    public static ThreadLocal<UUID> setRequestId(UUID requestId) {
        requestIdContext.set(requestId);
        return requestIdContext;
    }

    /**
     * 获取请求 ID
     *
     * @return 请求 ID
     */
    public static UUID getRequestId() {
        return requestIdContext.get();
    }

    /**
     * 清空当前上下文
     *
     * @return 被清空的上下文
     */
    public static ThreadLocal<UUID> clear() {
        requestIdContext.remove();
        return requestIdContext;
    }

}
