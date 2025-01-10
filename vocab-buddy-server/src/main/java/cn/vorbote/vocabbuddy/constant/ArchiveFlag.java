package cn.vorbote.vocabbuddy.constant;

/**
 * 删除标记
 * <p>
 * Created at 16:12, 23 May 2023
 *
 * @author Zihlu WANG
 */
public final class ArchiveFlag {

    private ArchiveFlag() {}

    /**
     * 未删除
     */
    public static final Integer NOT_ARCHIVED = 0;

    /**
     * 已删除
     */
    public static final Integer ARCHIVED = 1;
}
