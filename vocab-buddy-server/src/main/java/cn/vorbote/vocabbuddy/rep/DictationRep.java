package cn.vorbote.vocabbuddy.rep;

import cn.vorbote.vocabbuddy.model.proto.Dictation;
import cn.vorbote.vocabbuddy.model.vo.DictationVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * DictationRep
 * <p>
 * Created at 15:36, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@org.apache.ibatis.annotations.Mapper
public interface DictationRep extends BaseMapper<Dictation> {

    /**
     * 分页查询指定用户的听写记录
     *
     * @param offset 偏移量
     * @param size   页面大小
     * @param userId 被查询的用户
     * @return 查询到的听写记录
     */
    @Select("""
            select id,
                   user_id,
                   answering_mode,
                   generation_mode,
                   tag_id,
                   words_count,
                   correct_words_count,
                   incorrect_words_count,
                   start_time,
                   end_time,
                   create_at,
                   create_by,
                   update_at,
                   update_by,
                   archived
            from dictations
            where archived = 0
              and user_id = #{userId}
              and end_time is not null
            order by create_at desc
            limit #{offset}, #{size};
            """)
    List<Dictation> fetchPaginatedDictation(@Param("offset") Long offset,
                                            @Param("size") Long size,
                                            @Param("userId") Long userId);

    /**
     * 根据用户查询听写数量
     *
     * @param userId 被查询的用户ID
     * @return 听写测试数量
     */
    @Select("""
            select count(*)
            from dictations
            where archived = 0
              and user_id = #{userId}
              and end_time is not null;
            """)
    int countDictationsByUser(@Param("userId") Long userId);

    /**
     * 获取指定用户最近五次听写测试的ID
     *
     * @param userId 用户ID
     * @return 最近五次的ID
     */
    @Select("""
            select id
            from dictations
            where archived = 0
              and end_time is not null
              and user_id = #{userId}
            order by create_at desc
            limit 5;
            """)
    List<Long> fetchLatestDictations(@Param("userId") Long userId);

}
