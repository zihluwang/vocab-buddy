package cn.vorbote.vocabbuddy.schedule;

import cn.vorbote.vocabbuddy.service.DictationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * StopDictationTask
 * <p>
 * Created at 16:26, 26 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Component
public class StopDictationTask {

    private final TaskScheduler taskScheduler;

    private final DictationService dictationService;

    public StopDictationTask(TaskScheduler taskScheduler, DictationService dictationService) {
        this.taskScheduler = taskScheduler;
        this.dictationService = dictationService;
    }

    /**
     * 规划听写测试的自动停止
     *
     * @param dictationId 听写测试
     */
    public void scheduleTask(Long dictationId) {
        var endAt = LocalDateTime.now().plusHours(3);
        taskScheduler.schedule(() -> {
            if (dictationService.isExist(dictationId) && !dictationService.isDictationStopped(dictationId)) {
                dictationService.stopDictation(dictationId);
            }
        }, endAt.toInstant(ZoneOffset.ofHours(8)));
    }
}
