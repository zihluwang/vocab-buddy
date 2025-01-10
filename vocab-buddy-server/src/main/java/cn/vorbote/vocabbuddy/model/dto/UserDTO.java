package cn.vorbote.vocabbuddy.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * UserDTO
 * <p>
 * Created at 09:48, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class UserDTO {

    private Long id;

    private String username;

    private String email;

    private Integer region;

    private String phone;

    private Integer grade;

    private String password;

}
