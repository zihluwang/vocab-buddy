package cn.vorbote.vocabbuddy.filter;

import cn.vorbote.vocabbuddy.context.CommonContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * CommonInterceptor
 * <p>
 * Created at 11:48, 28 Mar 2023
 *
 * @author vorbote
 */
@Slf4j
@Component
public final class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final var requestId = UUID.randomUUID();
        CommonContext.setRequestId(requestId);

        log.info("请求[{}]，用户访问接口 {}", requestId, request.getRequestURI());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("请求[{}]，处理完毕...", CommonContext.getRequestId());

        CommonContext.clear();
    }
}
