package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.UserDTO;
import cn.vorbote.vocabbuddy.model.proto.User;
import cn.vorbote.vocabbuddy.model.vo.UserVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * UserMapper
 * <p>
 * Created at 09:49, 24 May 2023
 *
 * @author Zihlu WANG
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "userDTO.id"),
            @Mapping(target = "username", source = "userDTO.username"),
            @Mapping(target = "email", source = "userDTO.email"),
            @Mapping(target = "region", expression = "java(cn.vorbote.vocabbuddy.constant.Region.getByCodeOrThrow(userDTO.getRegion()))"),
            @Mapping(target = "phone", source = "userDTO.phone"),
            @Mapping(target = "password", source = "userDTO.password"),
            @Mapping(target = "grade", source = "userDTO.grade"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    User map(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(user.getId()))"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "region", expression = "java(user.getRegion().getLabel())"),
            @Mapping(target = "regionCode", expression = "java(user.getRegion().getCode())"),
            @Mapping(target = "phone", source = "user.phone"),
            @Mapping(target = "grade", source = "user.grade"),
    })
    UserVO map(User user);

}
