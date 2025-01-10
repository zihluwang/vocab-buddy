package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.vocabbuddy.model.proto.Tag;
import cn.vorbote.vocabbuddy.rep.TagRep;
import cn.vorbote.vocabbuddy.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TagServiceImpl
 * <p>
 * Created at 13:12, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagRep, Tag> implements TagService {
    @Override
    public List<Tag> fetchTagsByWordId(Long wordId) {
        return baseMapper.fetchTagsByWordId(wordId);
    }

    @Override
    public List<Tag> fetchAllTags() {
        return baseMapper.fetchAllTags();
    }
}
