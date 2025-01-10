package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.vocabbuddy.model.proto.WordDescription;
import cn.vorbote.vocabbuddy.rep.WordDescriptionRep;
import cn.vorbote.vocabbuddy.service.WordDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * WordDescriptionServiceImpl
 * <p>
 * Created at 14:47, 24 May 2023
 *
 * @author ziluw < zilu.wong@outlook.com >
 */
@Slf4j
@Service
public class WordDescriptionServiceImpl extends ServiceImpl<WordDescriptionRep, WordDescription> implements WordDescriptionService {
}
