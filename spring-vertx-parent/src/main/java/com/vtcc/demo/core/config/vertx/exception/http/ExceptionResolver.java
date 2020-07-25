package com.vtcc.demo.core.config.vertx.exception.http;

import com.vtcc.demo.core.config.vertx.exception.VertxSpringCoreException;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class ExceptionResolver {
    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

    public void resolveException(RoutingContext context, Throwable throwable) {
        HttpServerResponse response = context.response();
        if (throwable instanceof VertxSpringCoreException) {
            jsonErrorMessage(
                    response.setStatusCode(((VertxSpringCoreException) throwable).getStatusCode()),
                    throwable.getMessage());
        } else {
            jsonErrorMessage(
                    response.setStatusCode(INTERNAL_SERVER_ERROR.value()),
                    throwable.getMessage());
        }
    }

    protected void jsonErrorMessage(HttpServerResponse response, String message) {
        response.putHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8)
                .end(new JsonObject().put("error", message).toString());
    }
}
