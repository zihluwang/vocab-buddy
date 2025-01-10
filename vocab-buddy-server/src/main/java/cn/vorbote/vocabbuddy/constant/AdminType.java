package cn.vorbote.vocabbuddy.constant;

import cn.vorbote.web.exceptions.BizException;
import com.baomidou.mybatisplus.annotation.EnumValue;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;

/**
 * 管理员类型
 * <p>
 * Created at 16:02, 23 May 2023
 *
 * @author Zihlu WANG
 */
public enum AdminType {

    ADMIN(1, "管理员"),
    SUPER_ADMIN(2, "超级管理员"),
    ;

    @EnumValue
    private final Integer code;

    private final String label;

    AdminType(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    /**
     * 获取管理员代码
     *
     * @return 管理员代码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 获取管理员标识
     *
     * @return 管理员标识
     */
    public String getLabel() {
        return label;
    }

    /**
     * 根据管理员代码查找对应的管理员类型。
     *
     * @param code 需要查找的管理员类型代码
     * @return 如果找到了该管理员类型，返回包含该管理员类型的 {@code Optional} 对象；否则返回 {@code Optional.empty()}
     */
    private static Optional<AdminType> _getByCode(Integer code) {
        return Arrays.stream(values())
                .filter((value) -> code.equals(value.code))
                .findFirst();
    }

    /**
     * 根据管理员代码查找对应的管理员类型。
     *
     * @param code 需要查找的管理员类型代码
     * @return 如果找到了该管理员类型，返回包含该管理员类型的 {@code Optional} 对象；否则返回 {@code null}
     */
    public static AdminType getByCode(Integer code) {
        return _getByCode(code).orElse(null);
    }

    /**
     * 根据管理员代码查找对应的管理员类型。
     *
     * @param code         需要查找的管理员类型代码
     * @param defaultValue 默认的管理员类型
     * @return 如果找到了该管理员类型，返回包含该管理员类型；否则返回给定的管理员类型
     */
    public static AdminType getByCodeOrDefault(Integer code, AdminType defaultValue) {
        return _getByCode(code).orElse(defaultValue);
    }

    /**
     * 根据管理员代码查找对应的管理员类型。
     *
     * @param code 需要查找的管理员类型代码
     * @return 如果找到了该管理员类型，返回包含该管理员类型的 {@code Optional} 对象；否则抛出异常
     * @throws BizException 找不到相应管理员类型则抛出该异常
     */
    public static AdminType getByCodeOrThrow(Integer code) {
        return _getByCode(code)
                .orElseThrow(() -> new BizException(HttpServletResponse.SC_BAD_REQUEST, "找不到对应的地区！"));
    }
}
