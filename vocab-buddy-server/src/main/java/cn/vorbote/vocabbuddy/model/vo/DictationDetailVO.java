package cn.vorbote.vocabbuddy.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * DictationDetailVO
 * <p>
 * Created at 15:45, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class DictationDetailVO {

    private String id;

    private String dictationId;

    private String wordId;

    private String word;

    private List<WordDescriptionVO> wordDescriptions;

    private String userSpelling;

    private Integer correct;

}
