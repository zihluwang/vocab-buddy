package cn.vorbote.vocabbuddy.model.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * DictationDetailDTO
 * <p>
 * Created at 15:44, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class DictationDetailDTO {
    private Long id;

    private Long dictationId;

    private Long wordId;

    private String userSpelling;

    private Integer correct;
}
