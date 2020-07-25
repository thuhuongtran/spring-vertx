package com.vtcc.demo.core.config.vertx.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class VertxSpringCoreException extends Exception {
    protected int statusCode = INTERNAL_SERVER_ERROR.value();

    public VertxSpringCoreException(String message) {
        super(message);
    }

    public VertxSpringCoreException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
