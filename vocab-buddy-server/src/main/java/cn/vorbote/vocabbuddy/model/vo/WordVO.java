package cn.vorbote.vocabbuddy.model.vo;

import cn.vorbote.core.utils.CollectionUtil;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * WordVO
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
public final class WordVO {

    private String id;

    private String word;

    private List<WordDescriptionVO> wordDescriptions;

    private List<TagVO> tags;

    private List<String> choices;

    /**
     * 添加单词选项
     *
     * @param choice 选项
     * @return 被添加选项的单词视图层对象
     */
    public WordVO addChoice(String choice) {
        if (Objects.isNull(choices)) {
            choices = new ArrayList<>();
        }
        choices.add(choice);
        return this;
    }

    /**
     * 添加单词选项
     *
     * @param choices 选项
     * @return 被添加选项的单词视图层对象
     */
    public WordVO addChoices(String... choices) {
        if (choices.length > 0) {
            if (Objects.isNull(this.choices)) {
                this.choices = new ArrayList<>();
            }
            for (var choice : choices) {
                addChoice(choice);
            }
        }
        return this;
    }

}
