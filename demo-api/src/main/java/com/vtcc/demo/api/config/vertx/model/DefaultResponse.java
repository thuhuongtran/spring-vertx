package com.vtcc.demo.api.config.vertx.model;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class DefaultResponse<T> {
    private Integer code;
    private String message;
    private T result;

    public DefaultResponse() {
        this.code = 0;
        this.message = "OK";
    }

    public DefaultResponse(String message) {
        this.code = 0;
        this.message = message;
    }

    public DefaultResponse(String message, T result) {
        this.code = 0;
        this.message = message;
        this.result = result;
    }

    public DefaultResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseEntity<DefaultResponse<T>> okEntity(T body) {
        return ResponseEntity.ok(ok(body));
    }

    public static <T> DefaultResponse<T> ok(T body) {
        DefaultResponse<T> response = new DefaultResponse<>();
        response.setResult(body);
        return response;
    }
}
