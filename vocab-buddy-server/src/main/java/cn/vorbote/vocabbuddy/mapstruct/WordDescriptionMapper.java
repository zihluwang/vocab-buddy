package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.WordDescriptionDTO;
import cn.vorbote.vocabbuddy.model.proto.WordDescription;
import cn.vorbote.vocabbuddy.model.vo.WordDescriptionVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * WordDescriptionMapper
 * <p>
 * Created at 14:40, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface WordDescriptionMapper {

    @Mappings({
            @Mapping(target = "id", source = "wordDescriptionDTO.id"),
            @Mapping(target = "wordId", source = "wordDescriptionDTO.wordId"),
            @Mapping(target = "phonetics", source = "wordDescriptionDTO.phonetics"),
            @Mapping(target = "wordClass", expression = "java(cn.vorbote.vocabbuddy.constant.WordClass.getByCodeOrThrow(wordDescriptionDTO.getWordClass()))"),
            @Mapping(target = "meaning", source = "wordDescriptionDTO.meaning"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    WordDescription map(WordDescriptionDTO wordDescriptionDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(wordDescription.getId()))"),
            @Mapping(target = "wordId", expression = "java(String.valueOf(wordDescription.getWordId()))"),
            @Mapping(target = "phonetics", source = "wordDescription.phonetics"),
            @Mapping(target = "wordClass", expression = "java(wordDescription.getWordClass().getChineseName())"),
            @Mapping(target = "wordClassSign", expression = "java(wordDescription.getWordClass().getSign())"),
            @Mapping(target = "wordClassCode", expression = "java(wordDescription.getWordClass().getCode())"),
            @Mapping(target = "meaning", source = "wordDescription.meaning"),
    })
    WordDescriptionVO map(WordDescription wordDescription);

}
