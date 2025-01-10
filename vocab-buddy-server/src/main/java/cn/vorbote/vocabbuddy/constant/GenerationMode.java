package cn.vorbote.vocabbuddy.constant;

import cn.vorbote.web.exceptions.BizException;
import com.baomidou.mybatisplus.annotation.EnumValue;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;
import java.util.Optional;

/**
 * GenerationMode
 * <p>
 * Created at 15:13, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Getter
@ToString
public enum GenerationMode {
    
    RANDOM(1, "随机模式"),
    
    INCORRECT(2, "错词模式"),
    ;

    @EnumValue
    private final Integer code;
    
    private final String label;
    
    GenerationMode(Integer code, String label) {
        this.code = code;
        this.label = label;
    }
    
    /**
     * 根据词类代码查询词类。
     *
     * @param code 词类代码
     * @return 若找到了相应的词类，则返回 {@code Optional} 包装的词类；否则返回 {@code Optional.empty()}
     */
    public static Optional<GenerationMode> _getByCode(Integer code) {
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
    public static GenerationMode getByCode(Integer code) {
        return _getByCode(code).orElse(null);
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code         词类代码
     * @param defaultValue 默认词类
     * @return 若找到了相应的词类，则返回找到的词类；否则返回指定的默认词类
     */
    public static GenerationMode getByCodeOrDefault(Integer code, GenerationMode defaultValue) {
        return _getByCode(code).orElse(defaultValue);
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code 词类代码
     * @return 若找到了相应的词类，则返回找到的词类
     * @throws BizException 若找不到词类则抛出此异常
     */
    public static GenerationMode getByCodeOrThrow(Integer code) {
        return _getByCode(code)
                .orElseThrow(() -> new BizException(HttpServletResponse.SC_BAD_REQUEST, "找不到相关词类！"));
    }
    
}
