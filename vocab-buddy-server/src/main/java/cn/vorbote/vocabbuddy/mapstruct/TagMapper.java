package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.TagDTO;
import cn.vorbote.vocabbuddy.model.proto.Tag;
import cn.vorbote.vocabbuddy.model.vo.TagVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * TagMapper
 * <p>
 * Created at 13:09, 24 May 2023
 *
 * @author Zihlu WANG
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface TagMapper {

    @Mappings({
            @Mapping(target = "id", source = "tagDTO.id"),
            @Mapping(target = "name", source = "tagDTO.name"),
            @Mapping(target = "code", source = "tagDTO.code"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    Tag map(TagDTO tagDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(tag.getId()))"),
            @Mapping(target = "name", source = "tag.name"),
            @Mapping(target = "code", source = "tag.code"),
    })
    TagVO map(Tag tag);

}
