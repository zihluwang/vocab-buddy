package cn.vorbote.vocabbuddy.model.vo;

import cn.vorbote.vocabbuddy.constant.Region;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * UserVO
 * <p>
 * Created at 09:47, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class UserVO {

    private String id;

    private String username;

    private String email;

    private String region;

    private Integer regionCode;

    private String phone;

    private Integer grade;

}
