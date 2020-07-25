package com.vtcc.demo.core.config.vertx.binder;

import com.vtcc.demo.core.config.annotation.http.VertxRequestMapping;
import com.vtcc.demo.core.config.vertx.binder.param.handler.ParamHandler;
import com.vtcc.demo.core.config.vertx.exception.http.ExceptionResolver;
import com.vtcc.demo.core.config.vertx.handler.http.ControllerIOWrapper;
import com.vtcc.demo.core.config.vertx.handler.http.ControllerResponseResolver;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.core.annotation.AnnotatedElementUtils.getMergedAnnotation;

public class RequestBinder {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Router router;
    private final Set<BeanDefinition> controllers;
    private final ApplicationContext context;
    private final ParamHandler paramHandler;
    private final ExceptionResolver exceptionResolver;
    private final ControllerResponseResolver responseResolver;

    public RequestBinder(Router router,
                         Set<BeanDefinition> controllers,
                         ApplicationContext context) {
        this.router = router;
        this.controllers = controllers;
        this.context = context;
        this.paramHandler = context.getBean(ParamHandler.class);
        this.exceptionResolver = context.getBean(ExceptionResolver.class);
        this.responseResolver = context.getBean(ControllerResponseResolver.class);
    }

    public void bindRoutes() {
        logger.info("Scanning controller ...");
        for (BeanDefinition beanDef : controllers) {
            try {
                Class<?> beanClass = Class.forName(beanDef.getBeanClassName());
                Object objInstance = context.getBean(beanClass);
                Stream.of(beanClass.getMethods())
                        .filter(method -> AnnotatedElementUtils.isAnnotated(method, VertxRequestMapping.class))
                        .forEach(method -> {
                            logger.info("Method: {}", method.getName());
                            bindRequestRoute(objInstance, method);
                        });
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindRequestRoute(Object objInstance, Method method) {
        VertxRequestMapping requestMapping = getMergedAnnotation(method, VertxRequestMapping.class);
        String path = requestMapping.path();
        logger.info("Binding route path: {}", path);
        router.route(requestMapping.method(), path)
                .handler(routingContext ->
                        paramHandler.handle(routingContext, method).setHandler(result -> {
                            if (result.succeeded()) {
                                try {
                                    handleRequest(routingContext, objInstance, method, result.result());
                                } catch (Exception e) {
                                    exceptionResolver.resolveException(routingContext, e);
                                }
                            } else {
                                exceptionResolver.resolveException(routingContext, result.cause());
                            }
                        }));
    }

    private void handleRequest(RoutingContext context, Object objInstance, Method method, Object[] args) {
        try {
            Object returnValue = method.invoke(objInstance, args);
            responseResolver.resolve(new ControllerIOWrapper(returnValue, context));
        } catch (Exception e) {
            exceptionResolver.resolveException(context, e);
        }
    }
}
