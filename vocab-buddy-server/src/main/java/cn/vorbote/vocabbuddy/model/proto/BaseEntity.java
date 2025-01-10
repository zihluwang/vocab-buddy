package cn.vorbote.vocabbuddy.model.proto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public abstract class BaseEntity {

    protected LocalDateTime createAt;

    protected String createBy;

    protected LocalDateTime updateAt;

    protected String updateBy;

    @TableLogic
    protected Integer archived;
}