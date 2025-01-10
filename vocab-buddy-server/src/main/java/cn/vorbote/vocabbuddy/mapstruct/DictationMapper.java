package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.DictationDTO;
import cn.vorbote.vocabbuddy.model.proto.Dictation;
import cn.vorbote.vocabbuddy.model.vo.DictationVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * DictationMapper
 * <p>
 * Created at 15:22, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface DictationMapper {

    @Mappings({
            @Mapping(target = "id", source = "dictationDTO.id"),
            @Mapping(target = "userId", source = "dictationDTO.userId"),
            @Mapping(target = "answeringMode", expression = "java(cn.vorbote.vocabbuddy.constant.AnsweringMode.getByCodeOrDefault(dictationDTO.getAnsweringMode(), cn.vorbote.vocabbuddy.constant.AnsweringMode.COMPLETE))"),
            @Mapping(target = "generationMode", expression = "java(cn.vorbote.vocabbuddy.constant.GenerationMode.getByCodeOrDefault(dictationDTO.getGenerationMode(), cn.vorbote.vocabbuddy.constant.GenerationMode.RANDOM))"),
            @Mapping(target = "tagId", source = "dictationDTO.tagId"),
            @Mapping(target = "wordsCount", source = "dictationDTO.wordsCount"),
            @Mapping(target = "correctWordsCount", source = "dictationDTO.correctWordsCount"),
            @Mapping(target = "incorrectWordsCount", source = "dictationDTO.incorrectWordsCount"),
            @Mapping(target = "startTime", source = "dictationDTO.startTime"),
            @Mapping(target = "endTime", source = "dictationDTO.endTime"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    Dictation map(DictationDTO dictationDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(dictation.getId()))"),
            @Mapping(target = "userId", expression = "java(String.valueOf(dictation.getUserId()))"),
            @Mapping(target = "answeringMode", expression = "java(dictation.getAnsweringMode().getLabel())"),
            @Mapping(target = "answeringModeCode", expression = "java(dictation.getAnsweringMode().getCode())"),
            @Mapping(target = "generationMode", expression = "java(dictation.getGenerationMode().getLabel())"),
            @Mapping(target = "generationModeCode", expression = "java(dictation.getGenerationMode().getCode())"),
            @Mapping(target = "tagId", expression = "java(String.valueOf(dictation.getTagId()))"),
            @Mapping(target = "wordsCount", source = "dictation.wordsCount"),
            @Mapping(target = "correctWordsCount", source = "dictation.correctWordsCount"),
            @Mapping(target = "incorrectWordsCount", source = "dictation.incorrectWordsCount"),
            @Mapping(target = "startTime", source = "dictation.startTime"),
            @Mapping(target = "endTime", source = "dictation.endTime"),
            @Mapping(target = "dictationDetails", ignore = true),
            @Mapping(target = "wordLib", ignore = true),
    })
    DictationVO map(Dictation dictation);

}
