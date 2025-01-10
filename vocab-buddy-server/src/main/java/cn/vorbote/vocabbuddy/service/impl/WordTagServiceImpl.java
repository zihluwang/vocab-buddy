package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.vocabbuddy.model.proto.WordTag;
import cn.vorbote.vocabbuddy.rep.WordTagRep;
import cn.vorbote.vocabbuddy.service.WordTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * WordTagServiceImpl
 * <p>
 * Created at 15:02, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Slf4j
@Service
public class WordTagServiceImpl extends ServiceImpl<WordTagRep, WordTag> implements WordTagService {
}
