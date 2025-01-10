package cn.vorbote.vocabbuddy.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * AdminDTO
 * <p>
 * Created at 16:20, 23 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class AdminDTO {

    private Long id;

    /**
     * Administrator's username.
     */
    private String username;

    /**
     * Administrator's email.
     */
    private String email;

    private Integer region;

    private String phone;

    private Integer adminType;

    private String password;

}
