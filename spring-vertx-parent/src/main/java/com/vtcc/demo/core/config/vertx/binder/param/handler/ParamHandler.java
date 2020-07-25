package com.vtcc.demo.core.config.vertx.binder.param.handler;

import com.vtcc.demo.core.config.vertx.binder.param.extractor.ParamExtractor;
import io.vertx.reactivex.core.Future;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ParamHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ParamExtractor paramExtractor;

    public ParamHandler(ParamExtractor paramExtractor) {
        this.paramExtractor = paramExtractor;
    }

    public Future<Object[]> handle(RoutingContext routingContext, Method method) {
        Future<Object[]> future = Future.future();
        HttpServerRequest request = routingContext.request();
        request.bodyHandler(buffer -> {
            String body = buffer.toString("UTF-8");
            logger.info("request body: {}", body);
            try {
                future.complete(paramExtractor.extractArguments(method, body));
            } catch (Exception e) {
                future.fail(e);
            }
        });
        return future;
    }
}
