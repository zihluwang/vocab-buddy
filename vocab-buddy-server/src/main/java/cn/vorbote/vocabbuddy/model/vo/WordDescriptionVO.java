package cn.vorbote.vocabbuddy.model.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * WordDescriptionVO
 * <p>
 * Created at 14:39, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class WordDescriptionVO {

    private String id;

    private String wordId;

    private String phonetics;

    private String wordClass;

    private String wordClassSign;

    private Integer wordClassCode;

    private String meaning;

}
