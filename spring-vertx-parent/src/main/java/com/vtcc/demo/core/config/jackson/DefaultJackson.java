package com.vtcc.demo.core.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtcc.demo.core.config.vertx.exception.VertxSpringCoreException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class DefaultJackson {
    private static ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
    }

    public static ObjectMapper objectMapper() {
        return objectMapper;
    }

    public static Object parseJsonToObject(String body, Class<?> clazz) throws VertxSpringCoreException {
        try {
            return objectMapper.readValue(body, clazz);
        } catch (IOException e) {
            throw new VertxSpringCoreException("Input wrong", HttpStatus.BAD_REQUEST.value());
        }
    }
}
