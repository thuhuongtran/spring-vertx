package com.vtcc.demo.core.config.vertx.binder.param.extractor;

import com.vtcc.demo.core.config.vertx.exception.VertxSpringCoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import static com.vtcc.demo.core.config.jackson.DefaultJackson.parseJsonToObject;

@Component
public class ParamExtractor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Object[] extractArguments(Method method, String body) throws VertxSpringCoreException {
        Parameter[] params = method.getParameters();
        int argsSize = params.length;
        logger.info("{}", argsSize);
        final Object[] values = new Object[argsSize];
        for (int i = 0; i < argsSize; i++) {
            Parameter param = params[i];
            if (param.isAnnotationPresent(RequestBody.class)) {
                logger.info("Param: {} - {} - {}", param.getName(), param.getType(), body);
                values[i] = extractRequestBody(body, param);
            } else throw new VertxSpringCoreException("Cannot bind args value, considering annotation missing or wrong");
        }
        return values;
    }

    private Object extractRequestBody(String body, Parameter param) throws VertxSpringCoreException {
        Type type = param.getAnnotatedType().getType();
        if (type instanceof Class<?>) {
            Class<?> clazz = param.getType();
            return parseJsonToObject(body, clazz);
        } else throw new VertxSpringCoreException("Cannot bind args value");
    }
}
