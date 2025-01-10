package cn.vorbote.vocabbuddy.model.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * AdminVO
 * <p>
 * Created at 16:25, 23 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class AdminVO {

    private String id;

    private String username;

    private String email;

    private String region;

    private Integer regionCode;

    private String phone;

    private String adminType;

    private Integer adminTypeCode;
}
