package cn.vorbote.vocabbuddy.filter;

import cn.vorbote.simplejwt.AccessKeyUtil;
import cn.vorbote.vocabbuddy.constant.Header;
import cn.vorbote.vocabbuddy.constant.Region;
import cn.vorbote.vocabbuddy.constant.TokenSubject;
import cn.vorbote.vocabbuddy.context.AdminContext;
import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.vocabbuddy.context.UserContext;
import cn.vorbote.vocabbuddy.model.proto.Admin;
import cn.vorbote.vocabbuddy.model.proto.User;
import cn.vorbote.vocabbuddy.util.WebUtil;
import cn.vorbote.web.exceptions.BizException;
import cn.vorbote.web.model.ResponseResult;
import cn.vorbote.web.utils.BizAssert;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * UserInterceptor
 * <p>
 * Created at 11:43, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Component("adminInterceptor")
public class AdminInterceptor implements HandlerInterceptor {

    private final AccessKeyUtil accessKeyUtil;

    private final WebUtil webUtil;


    public AdminInterceptor(AccessKeyUtil accessKeyUtil,
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
            // 若当前用户为普通用户，本拦截器不进行检测，直接放行
            if (TokenSubject.SUBJECT_USER.equalsIgnoreCase(decodedToken.getSubject())) {
                return true;
            }
            // 提取用户信息
            var admin = accessKeyUtil.getBean(token, Admin.class);
            // 由于 Region 为自定义 Enum，因此需要手动处理后设置成其数据
            var region = Region.getByCodeOrThrow(Integer.parseInt(decodedToken.getClaim("region").asString()));
            admin.setRegion(region);

            // 检查数据
            BizAssert.notNull(admin.getId(), "无法从 Token 中获取管理员 ID！");
            BizAssert.hasText(admin.getUsername(), "无法从 Token 中获取管理员用户名！");
            BizAssert.notNull(admin.getRegion(), "无法从 Token 中获取管理员账户地区！");
            BizAssert.hasText(admin.getPhone(), "无法从 Token 中获取管理员手机号码！");
            BizAssert.notNull(admin.getAdminType(), "无法从 Token 中获取管理员权限！");

            // 将用户信息存放到上下文中
            AdminContext.setCurrentAdmin(admin);
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
        log.info("请求[{}]处理完成", CommonContext.getRequestId().toString());
    }
}
