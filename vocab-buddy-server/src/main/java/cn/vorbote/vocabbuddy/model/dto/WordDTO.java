package cn.vorbote.vocabbuddy.model.dto;

import cn.vorbote.web.utils.BizAssert;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * WordDTO
 * <p>
 * Created at 13:55, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class WordDTO {

    private Long id;

    private String word;

    private List<WordDescriptionDTO> wordDescriptions;

    public WordDTO addWordDescription(WordDescriptionDTO wordDescriptionDTO) {
        BizAssert.notNull(wordDescriptionDTO, "单词释义不能为空！");
        if (Objects.isNull(wordDescriptions)) {
            wordDescriptions = new ArrayList<>();
        }
        wordDescriptions.add(wordDescriptionDTO);
        return this;
    }

}
