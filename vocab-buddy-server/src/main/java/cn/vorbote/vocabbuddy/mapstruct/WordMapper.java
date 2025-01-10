package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.WordDTO;
import cn.vorbote.vocabbuddy.model.proto.Word;
import cn.vorbote.vocabbuddy.model.vo.WordVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * WordMapper
 * <p>
 * Created at 13:56, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface WordMapper {

    @Mappings({
            @Mapping(target = "id", source = "wordDTO.id"),
            @Mapping(target = "word", source = "wordDTO.word"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    Word map(WordDTO wordDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(word.getId()))"),
            @Mapping(target = "word", source = "word.word"),
            @Mapping(target = "wordDescriptions", ignore = true),
            @Mapping(target = "tags", ignore = true),
            @Mapping(target = "choices", ignore = true)
    })
    WordVO map(Word word);

}
