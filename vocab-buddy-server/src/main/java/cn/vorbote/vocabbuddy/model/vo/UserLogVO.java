package cn.vorbote.vocabbuddy.model.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * UserLogDTO
 * <p>
 * Created at 12:56, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class UserLogVO {

    private String id;

    private String userId;

    private String operation;

}
