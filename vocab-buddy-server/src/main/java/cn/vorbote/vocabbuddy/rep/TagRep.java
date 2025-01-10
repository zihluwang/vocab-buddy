package cn.vorbote.vocabbuddy.rep;

import cn.vorbote.vocabbuddy.model.proto.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TagRep
 * <p>
 * Created at 13:11, 24 May 2023
 *
 * @author Zihlu WANG
 */
@org.apache.ibatis.annotations.Mapper
public interface TagRep extends BaseMapper<Tag> {

    /**
     * 根据单词查找所有的标签
     *
     * @param wordId 单词ID
     * @return 找到的所有标签
     */
    @Select("""
            select id,
                   name,
                   code,
                   create_by,
                   create_at,
                   update_by,
                   update_at,
                   archived
            from tags
            where archived = 0
              and id in (select tag_id
                         from word_tags
                         where word_id = #{wordId});
            """)
    List<Tag> fetchTagsByWordId(@Param("wordId") Long wordId);

    /**
     * 查询所有标签
     *
     * @return 所有标签
     */
    @Select("""
            select id,
                   name,
                   code,
                   create_by,
                   create_at,
                   update_by,
                   update_at,
                   archived
            from tags
            where archived = 0;
            """)
    List<Tag> fetchAllTags();

}
