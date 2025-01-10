package cn.vorbote.vocabbuddy.model.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * TagVO
 * <p>
 * Created at 13:09, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class TagVO {

    private String id;

    private String name;

    private String code;
}
