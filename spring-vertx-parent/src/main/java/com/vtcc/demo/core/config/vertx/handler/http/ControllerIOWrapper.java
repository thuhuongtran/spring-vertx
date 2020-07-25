package com.vtcc.demo.core.config.vertx.handler.http;

import io.vertx.reactivex.ext.web.RoutingContext;
import lombok.Data;

@Data
public class ControllerIOWrapper {
    private Object returnValue;
    private RoutingContext routingContext;

    public ControllerIOWrapper() {
    }

    public ControllerIOWrapper(Object returnValue, RoutingContext routingContext) {
        this.returnValue = returnValue;
        this.routingContext = routingContext;
    }
}
