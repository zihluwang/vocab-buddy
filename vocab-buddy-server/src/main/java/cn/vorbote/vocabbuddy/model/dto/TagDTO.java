package cn.vorbote.vocabbuddy.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * TagDTO
 * <p>
 * Created at 13:08, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class TagDTO {

    private Long id;

    private String name;

    private String code;

}
