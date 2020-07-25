package com.vtcc.demo.core.config.vertx.binder;

import io.vertx.reactivex.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.util.Set;

@Component
public class VertxRouteCenterBinder {
    private String PACKAGE_NAME = "com.vtcc.demo";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApplicationContext context;

    public VertxRouteCenterBinder(ApplicationContext context) {
        this.context = context;
    }

    public void bind(Router router) {
        Set<BeanDefinition> controllers = scanClassByAnnotation(Controller.class);
        logger.info("Found all controllers: {}", controllers);
        new RequestBinder(router, controllers, context)
                .bindRoutes();
    }

    private Set<BeanDefinition> scanClassByAnnotation(Class<? extends Annotation> annotationType) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        return scanner.findCandidateComponents(PACKAGE_NAME);
    }
}
