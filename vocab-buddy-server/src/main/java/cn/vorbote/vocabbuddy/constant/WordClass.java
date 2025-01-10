package cn.vorbote.vocabbuddy.constant;

import cn.vorbote.vocabbuddy.model.proto.Word;
import cn.vorbote.vocabbuddy.rep.WordRep;
import cn.vorbote.web.exceptions.BizException;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

/**
 * WordClass
 * <p>
 * Created at 14:03, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Getter
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WordClass {

    NOUN(1, "Noun", "名词", "n"),
    PRONOUN(2, "Pronoun", "代词", "pron"),
    ADJECTIVE(3, "Adjective", "形容词", "adj"),
    VERB(4, "Verb", "动词", "v"),
    AUXILIARY_VERB(5, "Auxiliary verb", "助动词", "aux"),
    TRANSITIVE_VERB(6, "Transitive verb", "及物动词", "vt"),
    INTRANSITIVE_VERB(7, "Intransitive verb", "不及物动词", "vi"),
    ADVERB(8, "Adverb", "副词", "adv"),
    NUMERAL(9, "Numeral", "数词", "num"),
    ARTICLE(10, "Article", "冠词", "art"),
    PREPOSITION(11, "Preposition", "介词", "prep"),
    CONJUNCTION(12, "Conjunction", "连词", "conj"),
    INTERJECTION(13, "Interjection", "感叹词", "intj"),
    ABBREVIATION(14, "Abbreviation", "缩略词", "abbr"),
    PHRASES(15, "Phrases", "词组", "ph"),
    ;

    @EnumValue
    private final Integer code;

    private final String name;

    private final String chineseName;

    private final String sign;

    WordClass(Integer code, String name, String chineseName, String sign) {
        this.code = code;
        this.name = name;
        this.chineseName = chineseName;
        this.sign = sign;
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code 词类代码
     * @return 若找到了相应的词类，则返回 {@code Optional} 包装的词类；否则返回 {@code Optional.empty()}
     */
    public static Optional<WordClass> _getByCode(Integer code) {
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
    public static WordClass getByCode(Integer code) {
        return _getByCode(code).orElse(null);
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code         词类代码
     * @param defaultValue 默认词类
     * @return 若找到了相应的词类，则返回找到的词类；否则返回指定的默认词类
     */
    public static WordClass getByCodeOrDefault(Integer code, WordClass defaultValue) {
        return _getByCode(code).orElse(defaultValue);
    }

    /**
     * 根据词类代码查询词类。
     *
     * @param code 词类代码
     * @return 若找到了相应的词类，则返回找到的词类
     * @throws BizException 若找不到词类则抛出此异常
     */
    public static WordClass getByCodeOrThrow(Integer code) {
        return _getByCode(code)
                .orElseThrow(() -> new BizException(HttpServletResponse.SC_BAD_REQUEST, "找不到相关词类！"));
    }

}
