package cn.vorbote.vocabbuddy.rep;

import cn.vorbote.vocabbuddy.model.proto.WordDescription;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WordDescriptionRep
 * <p>
 * Created at 14:45, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@org.apache.ibatis.annotations.Mapper
public interface WordDescriptionRep extends BaseMapper<WordDescription> {

    /**
     * 批量插入单词解释
     *
     * @param wordDescriptions 单词解释
     * @return 插入的数据
     */
    @Insert("""
            <script>
            insert into word_descriptions(id, word_id, phonetics, word_class, meaning, create_at, create_by)
            values
            <foreach
                item="wordDescription" collection="wordDescriptions" index="index"
                separator="," nullable="true">
                (#{wordDescription.id}, #{wordDescription.wordId}, #{wordDescription.phonetics},
                #{wordDescription.wordClass}, #{wordDescription.meaning}, #{wordDescription.createAt},
                #{wordDescription.createBy})
            </foreach>;
            </script>
            """)
    int storeBatch(@Param("wordDescriptions") List<WordDescription> wordDescriptions);

}
