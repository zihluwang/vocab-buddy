package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.UserLogDTO;
import cn.vorbote.vocabbuddy.model.proto.UserLog;
import cn.vorbote.vocabbuddy.model.vo.UserLogVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * UserLogMapper
 * <p>
 * Created at 12:57, 24 May 2023
 *
 * @author Zihlu WANG
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface UserLogMapper {

    @Mappings({
            @Mapping(target = "id", source = "userLogDTO.id"),
            @Mapping(target = "userId", source = "userLogDTO.userId"),
            @Mapping(target = "operation", source = "userLogDTO.operation"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    UserLog map(UserLogDTO userLogDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(userLog.getId()))"),
            @Mapping(target = "userId", expression = "java(String.valueOf(userLog.getUserId()))"),
            @Mapping(target = "operation", source = "userLog.operation"),
    })
    UserLogVO map(UserLog userLog);

}
