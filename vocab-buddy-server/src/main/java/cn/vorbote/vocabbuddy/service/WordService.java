package cn.vorbote.vocabbuddy.service;

import cn.vorbote.vocabbuddy.model.dto.WordDTO;
import cn.vorbote.vocabbuddy.model.proto.Word;
import cn.vorbote.vocabbuddy.model.vo.WordVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * WordService
 * <p>
 * Created at 14:00, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
public interface WordService extends IService<Word> {

    /**
     * 分页查询特定标签下的单词数据。
     *
     * @param current 当前页面
     * @param size    当前页面数据量
     * @param tagId   标签ID
     * @return 查询到的分页单词数据
     */
    IPage<WordVO> fetchPaginatedWordsByTagId(Long current, Long size, Long tagId);

    /**
     * 根据标签 ID 统计单词数量
     *
     * @param tagId 标签 ID
     * @return 该标签下的单词数量
     */
    Integer countWordsByTagId(Long tagId);

    /**
     * 查询标签 ID 下的所有单词
     *
     * @param tagId 标签 ID
     * @return 查询到的所有单词
     */
    List<Word> fetchWordsByTagId(Long tagId);

    /**
     * 将从Excel文件中解析出的数据存放入数据库中
     *
     * @param userUploadedWords 解析出的用户上传的单词数据
     * @param tagId             标签ID
     * @return 存进数据库的单词
     */
    List<WordVO> uploadWords(List<WordDTO> userUploadedWords, Long tagId);
}
