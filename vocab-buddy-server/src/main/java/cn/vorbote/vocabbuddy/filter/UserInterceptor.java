package cn.vorbote.vocabbuddy.filter;

import cn.vorbote.simplejwt.AccessKeyUtil;
import cn.vorbote.vocabbuddy.constant.Header;
import cn.vorbote.vocabbuddy.constant.Region;
import cn.vorbote.vocabbuddy.constant.TokenSubject;
import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.vocabbuddy.context.UserContext;
import cn.vorbote.vocabbuddy.model.proto.User;
import cn.vorbote.vocabbuddy.util.WebUtil;
import cn.vorbote.web.exceptions.BizException;
import cn.vorbote.web.model.ResponseResult;
import cn.vorbote.web.utils.BizAssert;
import com.auth0.jwt.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * UserInterceptor
 * <p>
 * Created at 11:43, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Component("userInterceptor")
public class UserInterceptor implements HandlerInterceptor {

    private final AccessKeyUtil accessKeyUtil;

    private final WebUtil webUtil;


    public UserInterceptor(AccessKeyUtil accessKeyUtil,
                           WebUtil webUtil) {
        this.accessKeyUtil = accessKeyUtil;
        this.webUtil = webUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var token = request.getHeader(Header.AUTHORIZATION);
        BizAssert.hasText(token, HttpServletResponse.SC_UNAUTHORIZED, "无法获取用户身份信息，请登录后重试！");

        try {
            var decodedToken = accessKeyUtil.info(token);
            // 若当前用户为管理员，本拦截器不进行检测，直接放行
            if (TokenSubject.SUBJECT_ADMIN.equalsIgnoreCase(decodedToken.getSubject())) {
                return true;
            }
            // 提取用户信息
            var user = accessKeyUtil.getBean(token, User.class);
            // 由于 Region 为自定义 Enum，因此需要手动处理后设置成其数据
            var region = Region.getByCodeOrThrow(Integer.parseInt(decodedToken.getClaim("region").asString()));
            user.setRegion(region);

            // 检查数据
            BizAssert.notNull(user.getId(), "无法从 Token 中获取用户 ID！");
            BizAssert.hasText(user.getUsername(), "无法从 Token 中获取用户名！");
            BizAssert.notNull(user.getRegion(), "无法从 Token 中获取用户账户地区！");
            BizAssert.hasText(user.getPhone(), "无法从 Token 中获取用户手机号码！");
            BizAssert.notNull(user.getGrade(), "无法从 Token 中获取用户年级信息！");

            // 将用户信息存放到上下文中
            UserContext.setCurrentUser(user);
        } catch (AlgorithmMismatchException | SignatureVerificationException |
                 InvalidClaimException tamperedTokenException) {
            webUtil.render(response, ResponseResult
                    .<Void>error("您的令牌可能被篡改，请保护个人信息安全！")
                    .code(HttpServletResponse.SC_FORBIDDEN)
                    .requestId(CommonContext.getRequestId().toString()));
            return false;
        } catch (TokenExpiredException tokenExpiredException) {
            webUtil.render(response, ResponseResult
                    .<Void>error("您的身份信息已过期，请重新登录后再试！")
                    .code(HttpServletResponse.SC_UNAUTHORIZED)
                    .requestId(CommonContext.getRequestId().toString()));
            return false;
        } catch (BizException bizException) {
            webUtil.render(response, bizException
                    .respond()
                    .requestId(CommonContext.getRequestId().toString()));
        } catch (Exception exception) {
            webUtil.render(response, ResponseResult
                    .<Void>error("无法提取用户数据，请重新登录后再试！")
                    .code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .requestId(CommonContext.getRequestId().toString()));
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
        // log.info("请求[{}]处理完成", CommonContext.getRequestId().toString());
    }
}
