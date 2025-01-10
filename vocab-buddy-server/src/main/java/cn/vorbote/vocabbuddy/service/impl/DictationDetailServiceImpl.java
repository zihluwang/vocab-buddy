package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.vocabbuddy.model.proto.Dictation;
import cn.vorbote.vocabbuddy.model.proto.DictationDetail;
import cn.vorbote.vocabbuddy.rep.DictationDetailRep;
import cn.vorbote.vocabbuddy.rep.DictationRep;
import cn.vorbote.vocabbuddy.service.DictationDetailService;
import cn.vorbote.web.utils.BizAssert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DictationDetailServiceImpl
 * <p>
 * Created at 15:53, 26 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Service
public class DictationDetailServiceImpl extends ServiceImpl<DictationDetailRep, DictationDetail> implements DictationDetailService {

    private final DictationRep dictationRep;

    @Autowired
    public DictationDetailServiceImpl(DictationRep dictationRep) {
        this.dictationRep = dictationRep;
    }

    @Transactional
    @Override
    public int recordDictationDetail(DictationDetail dictationDetail) {
        // 保存听写详情
        BizAssert.isTrue(baseMapper.insert(dictationDetail) > 0, "保存听写记录失败！");
        var dictation = dictationRep.selectById(dictationDetail.getDictationId());
        if (dictationDetail.getCorrect() == 1) {
            dictation.addCorrectWordCount();
        } else {
            dictation.addIncorrectWordsCount();
        }
        BizAssert.isTrue(dictationRep.updateById(dictation) > 0, "听写记录保存失败！");
        return 1;
    }
}
