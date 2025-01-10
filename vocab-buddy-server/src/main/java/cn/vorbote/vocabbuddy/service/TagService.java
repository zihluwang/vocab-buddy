package cn.vorbote.vocabbuddy.service;

import cn.vorbote.vocabbuddy.model.proto.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * TagService
 * <p>
 * Created at 13:12, 24 May 2023
 *
 * @author Zihlu WANG
 */
public interface TagService extends IService<Tag> {

    /**
     * 找出该单词所有的标签
     *
     * @param wordId 单词的ID
     * @return 该单词所有的标签
     */
    List<Tag> fetchTagsByWordId(Long wordId);

    /**
     * 查询所有标签
     *
     * @return 所有标签
     */
    List<Tag> fetchAllTags();

}
