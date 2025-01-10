package cn.vorbote.vocabbuddy.controller;

import cn.vorbote.core.exceptions.NotImplementedException;
import cn.vorbote.vocabbuddy.context.CommonContext;
import cn.vorbote.web.exceptions.BizException;
import cn.vorbote.web.model.ResponseResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * GlobalExceptionHandler
 * <p>
 * Created at 10:10, 24 May 2023
 *
 * @author Zihlu WANG
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseResult<Void> handle(BizException bizException) {
        return bizException.respond().requestId(CommonContext.getRequestId().toString());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseResult<Void> handleSQLException(SQLException sqlException) {
        return ResponseResult
                .<Void>error(() -> String.format("出现异常。若多次出现该提示，请联系客服并提供如下信息：\n%s", sqlException.getMessage()))
                .requestId(CommonContext.getRequestId().toString());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseResult<Void> handleNullPointerException(NullPointerException nullPointerException) {
        return ResponseResult.<Void>error("缺少重要数据")
                .code(HttpServletResponse.SC_BAD_REQUEST)
                .requestId(CommonContext.getRequestId().toString());
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseResult<Void> handle(NotImplementedException niException) {
        return ResponseResult
                .<Void>error(niException.getMessage())
                .code(HttpServletResponse.SC_SERVICE_UNAVAILABLE)
                .requestId(CommonContext.getRequestId().toString());
    }

}
