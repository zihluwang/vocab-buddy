package cn.vorbote.vocabbuddy.rep;

import cn.vorbote.vocabbuddy.model.proto.Word;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * WordRep
 * <p>
 * Created at 13:59, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@org.apache.ibatis.annotations.Mapper
public interface WordRep extends BaseMapper<Word> {

    /**
     * 分页查询某个单词标签下的单词
     *
     * @param offset 偏移量
     * @param size   当前页面大小
     * @param tagId  标签ID
     * @return 查询到的单词
     */
    @Select("""
            select id,
                   word,
                   create_at,
                   create_by,
                   update_at,
                   update_by,
                   archived
            from words
            where archived = 0
              and id in (select word_id
                         from word_tags
                         where word_tags.archived = 0
                           and tag_id = #{tagId})
            order by id asc
            limit #{offset}, #{size};
            """)
    List<Word> fetchPaginatedWordByTagId(@Param("offset") Long offset, @Param("size") Long size, @Param("tagId") Long tagId);

    /**
     * 查询某个单词标签下的所有单词
     *
     * @param tagId 标签ID
     * @return 查询到的单词
     */
    @Select("""
            select id,
                   word,
                   create_at,
                   create_by,
                   update_at,
                   update_by,
                   archived
            from words
            where archived = 0
              and id in (select word_id
                         from word_tags
                         where word_tags.archived = 0
                           and tag_id = #{tagId})
            order by id asc;
            """)
    List<Word> fetchWordsByTagId(@Param("tagId") Long tagId);

    /**
     * 根据标签ID查询总条目数量
     *
     * @param tagId 标签ID
     * @return 查询到的总条目数
     */
    @Select("""
            select count(*)
            from words
            where archived = 0
              and id in (select word_id
                         from word_tags
                         where word_tags.archived = 0
                           and tag_id = #{tagId});
            """)
    int countWordsByTagId(@Param("tagId") Long tagId);

    @Select("""
            <script>
            select id,
                   word,
                   create_at,
                   create_by,
                   update_at,
                   update_by,
                   archived
            from words
            where archived = 0
              and id in
              <foreach collection="wordIds" item="id" index="index" open="(" close=")" separator=",">
                #{id}
              </foreach>;
            </script>
            """)
    List<Word> fetchWords(@Param("wordIds") List<Long> wordIds);

}
