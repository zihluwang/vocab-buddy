package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.vocabbuddy.model.proto.AdminLog;
import cn.vorbote.vocabbuddy.rep.AdminLogRep;
import cn.vorbote.vocabbuddy.service.AdminLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AdminLogServiceImpl
 * <p>
 * Created at 18:31, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Service
public class AdminLogServiceImpl extends ServiceImpl<AdminLogRep, AdminLog> implements AdminLogService {
}
