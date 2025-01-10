package cn.vorbote.vocabbuddy.controller;

import cn.vorbote.core.time.DateTime;
import cn.vorbote.vocabbuddy.context.CommonContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * CustomErrorController
 * <p>
 * Created at 10:03, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController {

    public CustomErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        var status = this.getStatus(request);

        return new ResponseEntity<>(new HashMap<>() {{
            put("code", status.value());
            put("message", status.getReasonPhrase());
            put("data", null);
            put("timestamp", DateTime.now().unix());
            put("requestId", Optional.ofNullable(CommonContext.getRequestId()).orElse(UUID.randomUUID()));
        }}, HttpStatus.OK);
    }
}
