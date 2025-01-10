package cn.vorbote.vocabbuddy.rep;

import cn.vorbote.vocabbuddy.model.proto.DictationDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * DictationDetailRep
 * <p>
 * Created at 15:53, 26 May 2023
 *
 * @author Zihlu WANG
 */
@org.apache.ibatis.annotations.Mapper
public interface DictationDetailRep extends BaseMapper<DictationDetail> {

    @Select("""
            <script>
            select word_id
            from dictation_details
            where archived = 0
              and correct = 0
              and dictation_id in
              <foreach collection="dictationIds" item="id" index="index" open="(" close=")" separator=",">
                #{id}
              </foreach>;
            </script>
            """)
    List<Long> fetchIncorrectWords(@Param("dictationIds") List<Long> dictationIds);

}
