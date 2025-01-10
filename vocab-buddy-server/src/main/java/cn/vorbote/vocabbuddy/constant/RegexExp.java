package cn.vorbote.vocabbuddy.constant;

/**
 * 正则表达式
 * <p>
 * Created at 15:31, 23 May 2023
 *
 * @author Zihlu WANG
 */
public final class RegexExp {

    private RegexExp() {}

    /**
     * 中国大陆电话号码
     */
    public static final String PHONE_NUMBER_CN = "^1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[012356789]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[012356789]\\d{2}|6[567]\\d{2}|4[45789]\\d{2})\\d{6}$";

    /**
     * 中国香港电话号码
     */
    public static final String PHONE_NUMBER_HK = "^[2-9][0-9]{7}$";

    /**
     * 中国澳门电话号码
     */
    public static final String PHONE_NUMBER_MO = "^6\\d{7}$";

    /**
     * 中国台湾电话号码
     */
    public static final String PHONE_NUMBER_TW = "^((?=(09))[0-9]{10})$";

}
