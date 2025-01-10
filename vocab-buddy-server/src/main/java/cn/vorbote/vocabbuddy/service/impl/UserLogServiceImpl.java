package cn.vorbote.vocabbuddy.service.impl;

import cn.vorbote.vocabbuddy.model.proto.UserLog;
import cn.vorbote.vocabbuddy.rep.UserLogRep;
import cn.vorbote.vocabbuddy.service.UserLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * UserLogServiceImpl
 * <p>
 * Created at 13:04, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogRep, UserLog> implements UserLogService {
}
