package cn.vorbote.vocabbuddy.entrypoint;

/**
 * 用户接入点，需要用户身份才能访问
 * <p>
 * Created at 00:40, 25 May 2023
 *
 * @author Zihlu WANG
 */
public final class UserEntrypoint {

    private UserEntrypoint() {}

    public static final String COMMON_ENTRYPOINT_PATTERN = "/user/**";

    public static final String GENERATE_DICTATION = "/dictation/generate";

    public static final String RECORD_DICTATION_DETAIL = "/dictation-detail/{dictationId}";

    public static final String STOP_DICTATION = "/dictation/{dictationId}/stop";

    public static final String RETRIEVE_HISTORICAL_DICTATIONS = "/dictation/historical-dictations";

    public static final String RETRIEVE_DICTATION = "/dictation/{dictationId}";
}
