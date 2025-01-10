package cn.vorbote.vocabbuddy.rep;

import cn.vorbote.vocabbuddy.model.proto.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * UserRepo
 * <p>
 * Created at 21:11, 23 May 2023
 *
 * @author Zihlu WANG
 */
@org.apache.ibatis.annotations.Mapper
public interface UserRep extends BaseMapper<User> {

    /**
     * 根据用户名从数据库中查找用户信息
     *
     * @param username 被查询的用户名
     * @return 查询到的用户，查询不到则为 {@code null}
     */
    @Select("""
            <script>
            select id,
                   username,
                   region,
                   phone,
                   email,
                   grade,
                   password,
                   create_at,
                   create_by,
                   update_at,
                   update_by,
                   archived
            from users
            where archived = 0
              and username = #{username};
            </script>
            """)
    User fetchUserByUsername(@Param("username") String username);

    /**
     * 根据用户名统计用户数量
     *
     * @param username 用户名
     * @return 根据用户名查询到的用户数量
     */
    @Select("""
            <script>
            select count(*)
            from users
            where archived = 0
              and username = #{username};
            </script>
            """)
    int countByUsername(@Param("username") String username);

    /**
     * 根据电话号码统计用户数量
     *
     * @param region 国际区号
     * @param phone  电话号码
     * @return 根据电话号码查询到的用户数量
     */
    @Select("""
            <script>
            select count(*)
            from users
            where archived = 0
              and region = #{region}
              and phone = #{phone};
            </script>
            """)
    int countByPhone(@Param("region") Integer region, @Param("phone") String phone);

    /**
     * 根据电子邮箱地址统计用户数量
     *
     * @param email 电子邮箱地址
     * @return 根据电子邮箱地址查询到的用户数量
     */
    @Select("""
            <script>
            select count(*)
            from users
            where archived = 0
              and email = #{email};
            </script>
            """)
    int countByEmail(@Param("email") String email);

}
