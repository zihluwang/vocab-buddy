package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.AdminLogDTO;
import cn.vorbote.vocabbuddy.model.proto.AdminLog;
import cn.vorbote.vocabbuddy.model.vo.AdminLogVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * AdminLogMapper
 * <p>
 * Created at 18:26, 24 May 2023
 *
 * @author Zihlu WANG
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface AdminLogMapper {

    @Mappings({
            @Mapping(target = "id", source = "adminLogDTO.id"),
            @Mapping(target = "adminId", source = "adminLogDTO.adminId"),
            @Mapping(target = "operation", source = "adminLogDTO.operation"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    AdminLog map(AdminLogDTO adminLogDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(adminLog.getId()))"),
            @Mapping(target = "adminId", expression = "java(String.valueOf(adminLog.getAdminId()))"),
            @Mapping(target = "operation", source = "adminLog.operation"),
    })
    AdminLogVO map(AdminLog adminLog);
}
