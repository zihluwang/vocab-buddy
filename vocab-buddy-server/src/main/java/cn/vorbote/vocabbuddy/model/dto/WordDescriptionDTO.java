package cn.vorbote.vocabbuddy.model.dto;

import cn.vorbote.vocabbuddy.constant.WordClass;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * WordDescriptionDTO
 * <p>
 * Created at 14:38, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class WordDescriptionDTO {

    private Long id;

    private Long wordId;

    private String phonetics;

    private Integer wordClass;

    private String meaning;

}
