package cn.vorbote.vocabbuddy.model.proto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Tag
 * <p>
 * Created at 13:07, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tags")
public final class Tag extends BaseEntity {

    @TableId(type = IdType.INPUT)
    private Long id;
    
    private String name;
    
    private String code;

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     * @return 被设置的对象
     */
    @Override
    public Tag setCreateAt(LocalDateTime createAt) {
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
    public Tag setCreateBy(String createBy) {
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
    public Tag setUpdateAt(LocalDateTime updateAt) {
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
    public Tag setUpdateBy(String updateBy) {
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
    public Tag setArchived(Integer archived) {
        super.setArchived(archived);
        return this;
    }
    
}
