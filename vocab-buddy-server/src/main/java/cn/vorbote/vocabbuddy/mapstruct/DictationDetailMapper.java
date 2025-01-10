package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.DictationDetailDTO;
import cn.vorbote.vocabbuddy.model.proto.DictationDetail;
import cn.vorbote.vocabbuddy.model.vo.DictationDetailVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * DictationDetailMapper
 * <p>
 * Created at 15:46, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface DictationDetailMapper {

    @Mappings({
            @Mapping(target = "id", source = "dictationDetailDTO.id"),
            @Mapping(target = "dictationId", source = "dictationDetailDTO.dictationId"),
            @Mapping(target = "wordId", source = "dictationDetailDTO.wordId"),
            @Mapping(target = "userSpelling", source = "dictationDetailDTO.userSpelling"),
            @Mapping(target = "correct", source = "dictationDetailDTO.correct"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    DictationDetail map(DictationDetailDTO dictationDetailDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(dictationDetail.getId()))"),
            @Mapping(target = "dictationId", expression = "java(String.valueOf(dictationDetail.getDictationId()))"),
            @Mapping(target = "wordId", expression = "java(String.valueOf(dictationDetail.getWordId()))"),
            @Mapping(target = "userSpelling", source = "dictationDetail.userSpelling"),
            @Mapping(target = "correct", source = "dictationDetail.correct"),
            @Mapping(target = "word", ignore = true),
            @Mapping(target = "wordDescriptions", ignore = true)
    })
    DictationDetailVO map(DictationDetail dictationDetail);
}
