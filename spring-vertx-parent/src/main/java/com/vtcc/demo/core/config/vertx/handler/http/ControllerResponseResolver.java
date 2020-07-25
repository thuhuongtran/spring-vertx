package com.vtcc.demo.core.config.vertx.handler.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtcc.demo.core.config.vertx.exception.VertxSpringCoreException;
import com.vtcc.demo.core.config.vertx.exception.http.ExceptionResolver;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.vtcc.demo.core.config.jackson.DefaultJackson.objectMapper;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Component
public class ControllerResponseResolver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExceptionResolver exceptionResolver;

    public ControllerResponseResolver(ExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    public void resolve(ControllerIOWrapper ioWrapper) {
        Object returnValue = ioWrapper.getReturnValue();
        RoutingContext context = ioWrapper.getRoutingContext();
        HttpServerResponse response = context.response();
        if (returnValue instanceof Single) {
            Single<ResponseEntity> responseResult = (Single<ResponseEntity>) returnValue;
            System.out.println(String.format("Main Thread => %s", Thread.currentThread().getName()));
            responseResult
                    .subscribe(responseEntity -> handleResponse(response, responseEntity),
                            throwable -> exceptionResolver.resolveException(context, throwable));
        } else {
            exceptionResolver.resolveException(context,
                    new VertxSpringCoreException("unsupported return type (not Single)"));
        }
    }

    private void handleResponse(HttpServerResponse response, ResponseEntity responseEntity) throws JsonProcessingException {
        logger.info("response thread: {}, {}", Thread.currentThread().getId(), Thread.currentThread().getName());
        Object responseBody = responseEntity.getBody();
        if (responseBody == null) {
            nullBodyResponse(response);
        } else {
            nonNullBodyResponse(response, responseBody, responseEntity);
        }
    }

    private void nullBodyResponse(HttpServerResponse response) {
        response.setStatusCode(HttpStatus.NOT_FOUND.value())
                .putHeader(CONTENT_TYPE, APPLICATION_JSON)
                .end();
    }

    private void nonNullBodyResponse(HttpServerResponse response, Object responseBody, ResponseEntity responseEntity) throws JsonProcessingException {
        if (!(responseBody instanceof String)) {
            logger.info("Response body: {}", responseBody.toString());
            responseBody = objectMapper().writeValueAsString(responseBody);
        }
        response.setStatusCode(responseEntity.getStatusCodeValue())
                .putHeader(CONTENT_TYPE, APPLICATION_JSON)
                .end(responseBody.toString());
    }
}
