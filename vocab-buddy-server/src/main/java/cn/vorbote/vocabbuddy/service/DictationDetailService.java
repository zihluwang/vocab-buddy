package cn.vorbote.vocabbuddy.service;

import cn.vorbote.vocabbuddy.model.proto.DictationDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * DictationDetailService
 * <p>
 * Created at 15:52, 26 May 2023
 *
 * @author Zihlu WANG
 */
public interface DictationDetailService extends IService<DictationDetail> {

    /**
     * 保存听写记录详情
     *
     * @param dictationDetail 听写记录详情
     * @return 如果保存成功，则返回 {@code 1}；否则将跑出异常
     * @throws cn.vorbote.web.exceptions.BizException 保存失败则抛出次异常
     */
    int recordDictationDetail(DictationDetail dictationDetail);

}
