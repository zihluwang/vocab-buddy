package cn.vorbote.vocabbuddy.model.vo;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * WordTagVO
 * <p>
 * Created at 14:56, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class WordTagVO {

    private String id;

    private String wordId;

    private String tagId;

}
