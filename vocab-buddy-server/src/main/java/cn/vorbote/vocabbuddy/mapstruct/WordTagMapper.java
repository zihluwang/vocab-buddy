package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.WordTagDTO;
import cn.vorbote.vocabbuddy.model.proto.WordTag;
import cn.vorbote.vocabbuddy.model.vo.WordTagVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * WordTagMapper
 * <p>
 * Created at 14:57, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface WordTagMapper {

    @Mappings({
            @Mapping(target = "id", source = "wordTagDTO.id"),
            @Mapping(target = "wordId", source = "wordTagDTO.wordId"),
            @Mapping(target = "tagId", source = "wordTagDTO.tagId"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    WordTag map(WordTagDTO wordTagDTO);

    @Mappings({
            @Mapping(target = "id",     expression = "java(String.valueOf(wordTag.getId()))"),
            @Mapping(target = "wordId", expression = "java(String.valueOf(wordTag.getWordId()))"),
            @Mapping(target = "tagId",  expression = "java(String.valueOf(wordTag.getTagId()))"),
    })
    WordTagVO map(WordTag wordTag);

}
