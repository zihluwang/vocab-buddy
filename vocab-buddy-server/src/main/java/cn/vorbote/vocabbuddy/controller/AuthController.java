package cn.vorbote.vocabbuddy.controller;

import cn.vorbote.core.time.TimeSpan;
import cn.vorbote.simplejwt.AccessKeyUtil;
import cn.vorbote.vocabbuddy.constant.Header;
import cn.vorbote.vocabbuddy.constant.RegexExp;
import cn.vorbote.vocabbuddy.constant.Region;
import cn.vorbote.vocabbuddy.constant.TokenSubject;
import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.vocabbuddy.mapstruct.AdminMapper;
import cn.vorbote.vocabbuddy.mapstruct.UserMapper;
import cn.vorbote.vocabbuddy.model.dto.UserDTO;
import cn.vorbote.vocabbuddy.model.web.request.LoginRequest;
import cn.vorbote.vocabbuddy.model.vo.AdminVO;
import cn.vorbote.vocabbuddy.model.vo.UserVO;
import cn.vorbote.vocabbuddy.service.AdminService;
import cn.vorbote.vocabbuddy.service.UserService;
import cn.vorbote.web.model.ResponseResult;
import cn.vorbote.web.utils.BizAssert;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 认证控制器
 * <p>
 * Created at 22:10, 23 May 2023
 *
 * @author Zihlu WANG
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccessKeyUtil accessKeyUtil;

    private final UserService userService;

    private final AdminService adminService;

    private final AdminMapper adminMapper;

    private final UserMapper userMapper;

    private List<Region> supportedRegions;

    @Autowired
    public AuthController(AccessKeyUtil accessKeyUtil,
                          UserService userService,
                          AdminService adminService,
                          AdminMapper adminMapper,
                          UserMapper userMapper) {
        this.accessKeyUtil = accessKeyUtil;
        this.userService = userService;
        this.adminService = adminService;
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
    }

    @Autowired
    public void setSupportedRegions(List<Region> supportedRegions) {
        this.supportedRegions = supportedRegions;
    }

    /**
     * 管理员登录
     */
    @PostMapping("/admin/login")
    public ResponseResult<AdminVO> adminLogin(HttpServletResponse response,
                                              @RequestBody LoginRequest loginRequest) throws IllegalAccessException {
        var admin = adminService.fetchAdmin(loginRequest.username(), loginRequest.password());

        // 创建 Token
        var token = accessKeyUtil.createTokenWithBean(
                TimeSpan.builder().hours(3).build(),
                TokenSubject.SUBJECT_ADMIN,
                admin.composeDisplayName(),
                admin
        );
        response.addHeader(Header.AUTHORIZATION, token);

        return ResponseResult
                .success(adminMapper.map(admin), "欢迎 %s 使用 Vocab Buddy".formatted(admin.composeDisplayName()))
                .requestId(CommonContext.getRequestId().toString());
    }

    /**
     * 用户登录
     */
    @PostMapping("/user/login")
    public ResponseResult<UserVO> userLogin(HttpServletResponse response,
                                            @RequestBody LoginRequest loginRequest) throws IllegalAccessException {
        var user = userService.fetchUser(loginRequest.username(), loginRequest.password());

        // 创建 Token
        var token = accessKeyUtil.createTokenWithBean(
                TimeSpan.builder().hours(3).build(),
                TokenSubject.SUBJECT_USER,
                user.composeDisplayName(),
                user
        );
        response.addHeader(Header.AUTHORIZATION, token);

        return ResponseResult
                .success(userMapper.map(user), "欢迎 %s 使用 Vocab Buddy".formatted(user.composeDisplayName()))
                .requestId(CommonContext.getRequestId().toString());
    }

    @PostMapping("/user/register")
    public ResponseResult<UserVO> userRegister(HttpServletResponse response,
                                               @RequestBody UserDTO userParam) throws IllegalAccessException {
        // 检查用户数据
        BizAssert.notNull(userParam, "用户数据不能为空！");
        BizAssert.hasText(userParam.getUsername(), "用户名缺失，无法注册！");
        BizAssert.hasText(userParam.getPassword(), "密码缺失，无法注册！");
        BizAssert.notNull(userParam.getRegion(), "所在地区不能为空！");
        BizAssert.notNull(userParam.getGrade(), "年级信息不能为空！");

        // 检查地区与手机号
        var region = Region.getByCodeOrThrow(userParam.getRegion());
        // 检查地区是否受到支持
        BizAssert.isTrue(supportedRegions.contains(region), "对不起，我们的服务当前仅对中国开放！");
        // 检查手机号是否满足格式要求
        BizAssert.hasText(userParam.getPhone(), "电话号码缺失，无法注册！");
        BizAssert.isTrue(switch (region) {
            case CHINA_MAINLAND -> userParam.getPhone().matches(RegexExp.PHONE_NUMBER_CN);
            case HONG_KONG -> userParam.getPhone().matches(RegexExp.PHONE_NUMBER_HK);
            case MACAU -> userParam.getPhone().matches(RegexExp.PHONE_NUMBER_MO);
            case TAIWAN -> userParam.getPhone().matches(RegexExp.PHONE_NUMBER_TW);
            default -> false;
        }, "电话号码格式错误！");

        // 将用户信息保存到数据库
        var user = userService.register(userMapper.map(userParam));

        // 生成 Token
        var token = accessKeyUtil.createTokenWithBean(
                TimeSpan.builder().hours(3).build(),
                TokenSubject.SUBJECT_USER,
                user.composeDisplayName(),
                user
        );
        response.addHeader(Header.AUTHORIZATION, token);

        return ResponseResult
                .success(userMapper.map(user), "注册成功！")
                .requestId(CommonContext.getRequestId().toString());
    }

}
