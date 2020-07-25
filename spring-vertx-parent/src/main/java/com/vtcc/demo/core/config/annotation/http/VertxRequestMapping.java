package com.vtcc.demo.core.config.annotation.http;

import io.vertx.core.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface VertxRequestMapping {
    HttpMethod method() default HttpMethod.GET;

    String path() default "/";
}
