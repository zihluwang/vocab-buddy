package cn.vorbote.vocabbuddy.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * WordTagDTO
 * <p>
 * Created at 14:52, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class WordTagDTO {

    private Long id;

    private Long wordId;

    private Long tagId;

}
