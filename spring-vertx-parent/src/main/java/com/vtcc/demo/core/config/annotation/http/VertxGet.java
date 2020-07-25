package com.vtcc.demo.core.config.annotation.http;

import io.vertx.core.http.HttpMethod;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@VertxRequestMapping(method = HttpMethod.GET)
public @interface VertxGet {

    @AliasFor(annotation = VertxRequestMapping.class, attribute = "path")
    String value() default "/";
}
