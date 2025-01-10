package cn.vorbote.vocabbuddy.model.dto;

import cn.vorbote.vocabbuddy.constant.AnsweringMode;
import cn.vorbote.vocabbuddy.constant.GenerationMode;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * DictationDTO
 * <p>
 * Created at 15:19, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class DictationDTO {

    private Long id;

    private Long userId;

    private Integer answeringMode;

    private Integer generationMode;

    private Long tagId;

    private Integer wordsCount;

    private Integer correctWordsCount;

    private Integer incorrectWordsCount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
