package cn.vorbote.vocabbuddy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * WebUtil
 * <p>
 * Created at 12:05, 24 May 2023
 *
 * @author Zihlu WANG
 */
@Component
public final class WebUtil {

    private final ObjectMapper objectMapper;

    public WebUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void render(HttpServletResponse response, Object object) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(object));
    }
}
