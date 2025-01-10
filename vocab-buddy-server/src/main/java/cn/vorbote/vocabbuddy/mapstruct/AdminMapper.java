package cn.vorbote.vocabbuddy.mapstruct;

import cn.vorbote.vocabbuddy.model.dto.AdminDTO;
import cn.vorbote.vocabbuddy.model.proto.Admin;
import cn.vorbote.vocabbuddy.model.vo.AdminVO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * AdminMapper
 * <p>
 * Created at 15:36, 23 May 2023
 *
 * @author Zihlu WANG
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mappings({
            @Mapping(target = "id", source = "adminDTO.id"),
            @Mapping(target = "username", source = "adminDTO.username"),
            @Mapping(target = "email", source = "adminDTO.email"),
            @Mapping(target = "region", expression = "java(cn.vorbote.vocabbuddy.constant.Region.getByCodeOrThrow(adminDTO.getRegion()))"),
            @Mapping(target = "phone", source = "adminDTO.phone"),
            @Mapping(target = "adminType", expression = "java(cn.vorbote.vocabbuddy.constant.AdminType.getByCodeOrThrow(adminDTO.getAdminType()))"),
            @Mapping(target = "password", source = "adminDTO.password"),
            @Mapping(target = "createAt", ignore = true),
            @Mapping(target = "createBy", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "updateBy", ignore = true),
            @Mapping(target = "archived", ignore = true),
    })
    Admin map(AdminDTO adminDTO);

    @Mappings({
            @Mapping(target = "id", expression = "java(String.valueOf(admin.getId()))"),
            @Mapping(target = "username", source = "admin.username"),
            @Mapping(target = "email", source = "admin.email"),
            @Mapping(target = "region", expression = "java(admin.getRegion().getLabel())"),
            @Mapping(target = "regionCode", expression = "java(admin.getRegion().getCode())"),
            @Mapping(target = "phone", source = "admin.phone"),
            @Mapping(target = "adminType", expression = "java(admin.getAdminType().getLabel())"),
            @Mapping(target = "adminTypeCode", expression = "java(admin.getAdminType().getCode())"),
    })
    AdminVO map(Admin admin);

}
