package cn.vorbote.vocabbuddy.model.proto;

import cn.vorbote.vocabbuddy.constant.AnsweringMode;
import cn.vorbote.vocabbuddy.constant.GenerationMode;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Dictation
 * <p>
 * Created at 15:04, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("dictations")
public final class Dictation extends BaseEntity {

    @TableId(type = IdType.INPUT)
    private Long id;

    private Long userId;

    private AnsweringMode answeringMode;

    private GenerationMode generationMode;

    private Long tagId;

    private Integer wordsCount;

    private Integer correctWordsCount;

    private Integer incorrectWordsCount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     * @return 被设置的对象
     */
    @Override
    public Dictation setCreateAt(LocalDateTime createAt) {
        super.setCreateAt(createAt);
        return this;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     * @return 被设置的对象
     */
    @Override
    public Dictation setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }

    /**
     * 设置上次更新时间
     *
     * @param updateAt 上次更新时间
     * @return 被设置的对象
     */
    @Override
    public Dictation setUpdateAt(LocalDateTime updateAt) {
        super.setUpdateAt(updateAt);
        return this;
    }

    /**
     * 设置上次更新人
     *
     * @param updateBy 上次更新人
     * @return 被设置的对象
     */
    @Override
    public Dictation setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }

    /**
     * 设置删除标记
     *
     * @param archived 删除标记，请调用 {@link cn.vorbote.vocabbuddy.constant.ArchiveFlag} 中的常量数据
     * @return 被设置的对象
     */
    @Override
    public Dictation setArchived(Integer archived) {
        super.setArchived(archived);
        return this;
    }

    /**
     * 增加正确单词数量
     *
     * @return 正确单词数量
     */
    public Dictation addCorrectWordCount() {
        this.correctWordsCount++;
        return this;
    }

    /**
     * 增加错误单词数量
     *
     * @return 错误单词数量
     */
    public Dictation addIncorrectWordsCount() {
        this.incorrectWordsCount++;
        return this;
    }
    
}
