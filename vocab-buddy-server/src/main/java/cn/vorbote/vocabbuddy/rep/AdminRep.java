package cn.vorbote.vocabbuddy.rep;

import cn.vorbote.vocabbuddy.model.proto.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 管理员数据访问对象
 * <p>
 * Created at 15:36, 23 May 2023
 *
 * @author Zihlu WANG
 */
@org.apache.ibatis.annotations.Mapper
public interface AdminRep extends BaseMapper<Admin> {

    /**
     * 根据用户名获取管理员
     *
     * @param username 管理员的用户名
     * @return 查询到的管理员，查询不到则为 {@code null}
     */
    @Select("""
            <script>
            select id,
                   username,
                   email,
                   region,
                   phone,
                   admin_type,
                   password,
                   create_at,
                   create_by,
                   update_at,
                   update_by,
                   archived
            from administrators
            where archived = 0
              and username = #{username};
            </script>
            """)
    Admin fetchAdminByUsername(@Param("username") String username);

}
