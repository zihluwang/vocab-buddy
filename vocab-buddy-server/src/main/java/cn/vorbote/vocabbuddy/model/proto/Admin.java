package cn.vorbote.vocabbuddy.model.proto;

import cn.vorbote.simplejwt.annotations.JwtIgnore;
import cn.vorbote.vocabbuddy.constant.AdminType;
import cn.vorbote.vocabbuddy.constant.Region;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 管理员
 * <p>
 * Created at 15:35, 23 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("administrators")
public final class Admin extends BaseEntity {

    /**
     * Administrator ID.
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * Administrator's username.
     */
    private String username;

    /**
     * Administrator's email.
     */
    private String email;

    private Region region;

    private String phone;

    private AdminType adminType;

    @JwtIgnore
    private String password;

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     * @return 被设置的对象
     */
    @Override
    public Admin setCreateAt(LocalDateTime createAt) {
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
    public Admin setCreateBy(String createBy) {
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
    public Admin setUpdateAt(LocalDateTime updateAt) {
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
    public Admin setUpdateBy(String updateBy) {
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
    public Admin setArchived(Integer archived) {
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
