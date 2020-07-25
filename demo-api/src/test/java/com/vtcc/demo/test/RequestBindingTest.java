package com.vtcc.demo.test;

import com.vtcc.demo.api.controller.DepartmentController;
import com.vtcc.demo.core.config.vertx.binder.RequestBinder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RequestBindingTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getAllMethod() {
        Arrays.stream(DepartmentController.class.getMethods())
                .forEach(m -> logger.info("{} - {}", m.getDeclaringClass(), m.getName()));
    }
}
