package com.vtcc.demo.commons.repository.impl;

import com.vtcc.demo.commons.data.model.spring_vertx_demo.tables.pojos.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DslFake {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public int fakeCount() {
        logger.info("Dsl count thread rx: {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName());
        return (int) Thread.currentThread().getId();
    }

    public List<Department> fakeList() {
        logger.info("Dsl get all thread rx: {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
        List<Department> departments = new ArrayList<>();
        Department department = new Department();
        department.setId(1);
        department.setName("vtcc");
        departments.add(department);
        return departments;
    }
}
