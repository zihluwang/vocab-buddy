package cn.vorbote.vocabbuddy.model.vo;

import cn.vorbote.core.utils.CollectionUtil;
import cn.vorbote.vocabbuddy.constant.AnsweringMode;
import cn.vorbote.vocabbuddy.constant.GenerationMode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DictationVO
 * <p>
 * Created at 15:20, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class DictationVO {

    private String id;

    private String userId;

    private String answeringMode;

    private Integer answeringModeCode;

    private String generationMode;

    private Integer generationModeCode;

    private String tagId;

    private Integer wordsCount;

    private Integer correctWordsCount;

    private Integer incorrectWordsCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private List<DictationDetailVO> dictationDetails;

    private List<WordVO> wordLib;

    /**
     * 添加一个测试详情
     *
     * @param dictationDetailVO 测试详情数据
     * @return 被添加的听写测试实例
     */
    public DictationVO addDictationDetail(DictationDetailVO dictationDetailVO) {
        if (CollectionUtils.isEmpty(dictationDetails)) {
            dictationDetails = new ArrayList<>();
        }
        dictationDetails.add(dictationDetailVO);
        return this;
    }
}
