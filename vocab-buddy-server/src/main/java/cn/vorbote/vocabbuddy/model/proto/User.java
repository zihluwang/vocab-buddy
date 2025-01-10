package cn.vorbote.vocabbuddy.model.proto;

import cn.vorbote.simplejwt.annotations.JwtIgnore;
import cn.vorbote.vocabbuddy.constant.Region;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户
 * <p>
 * Created at 15:34, 23 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("users")
public final class User extends BaseEntity {

    @TableId(type = IdType.INPUT)
    private Long id;

    private String username;

    private String email;

    private Region region;

    private String phone;

    private Integer grade;

    @JwtIgnore
    private String password;

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     * @return 被设置的对象
     */
    @Override
    public User setCreateAt(LocalDateTime createAt) {
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
    public User setCreateBy(String createBy) {
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
    public User setUpdateAt(LocalDateTime updateAt) {
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
    public User setUpdateBy(String updateBy) {
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
    public User setArchived(Integer archived) {
        super.setArchived(archived);
        return this;
    }

    /**
     * 获取显示名称
     *
     * @return 显示名称
     */
    public String composeDisplayName() {
        return "%s[%d]".formatted(username, id);
    }
}
