package cn.vorbote.vocabbuddy.constant;

import cn.vorbote.web.exceptions.BizException;
import com.baomidou.mybatisplus.annotation.EnumValue;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

/**
 * AnsweringMode
 * <p>
 * Created at 15:07, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Getter
@ToString
public enum AnsweringMode {

    /**
     * 选择题模式
     */
    CHOICE(1, "选择题"),

    /**
     * 填空题模式
     */
    FILL(2, "填空题"),

    /**
     * 全部默写模式
     */
    COMPLETE(3, "默写题"),
    ;

    @EnumValue
    private final Integer code;

    private final String label;

    AnsweringMode(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code 词类代码
     * @return 若找到了相应的词类，则返回 {@code Optional} 包装的词类；否则返回 {@code Optional.empty()}
     */
    public static Optional<AnsweringMode> _getByCode(Integer code) {
        return Arrays.stream(values())
                .filter((value) -> code.equals(value.code))
                .findFirst();
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code 词类代码
     * @return 若找到了相应的词类，则返回找到的词类；否则返回 {@code null}
     */
    public static AnsweringMode getByCode(Integer code) {
        return _getByCode(code).orElse(null);
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code         词类代码
     * @param defaultValue 默认词类
     * @return 若找到了相应的词类，则返回找到的词类；否则返回指定的默认词类
     */
    public static AnsweringMode getByCodeOrDefault(Integer code, AnsweringMode defaultValue) {
        return _getByCode(code).orElse(defaultValue);
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code 词类代码
     * @return 若找到了相应的词类，则返回找到的词类
     * @throws BizException 若找不到词类则抛出此异常
     */
    public static AnsweringMode getByCodeOrThrow(Integer code) {
        return _getByCode(code)
                .orElseThrow(() -> new BizException(HttpServletResponse.SC_BAD_REQUEST, "找不到相关词类！"));
    }

}
