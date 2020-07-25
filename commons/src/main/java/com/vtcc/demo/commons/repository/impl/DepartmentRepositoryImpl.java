package com.vtcc.demo.commons.repository.impl;

import com.vtcc.demo.commons.data.model.spring_vertx_demo.tables.pojos.Department;
import com.vtcc.demo.commons.repository.DepartmentRepository;
import io.reactivex.Single;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vtcc.demo.commons.data.model.spring_vertx_demo.tables.Department.DEPARTMENT;
import static com.vtcc.demo.core.service.VertxExecution.rxBlocking;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DSLContext dsl;
    @Autowired
    private DslFake dslFake;

    @Override
    public Single<List<Department>> getAllDepartment() {
        logger.info("Repo get all- thread id:  {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
        List<Department> departmentGroup = dsl.select()
                .from(DEPARTMENT)
                .fetch()
                .into(Department.class);
        logger.info("All departments: {}", departmentGroup.size());
        return rxBlocking(departmentGroup);
    }

    @Override
    public Single<Boolean> insertDepartment(String name) {
        int insert = dsl.insertInto(DEPARTMENT)
                .columns(DEPARTMENT.NAME)
                .values(DSL.value(name))
                .execute();
        logger.info("Insert: {}", insert);
        return rxBlocking(insert > 0);
    }

    @Override
    public Single<Integer> countAllDepartment() {
        logger.info("Repo count- thread id:  {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
        int count = dsl.selectCount()
                .from(DEPARTMENT)
                .fetchOne(0, int.class);
        logger.info("count: {}", count);
        return rxBlocking(count);
    }

    @Override
    public Single<List<Department>> rxGetAllDepartment() {
        logger.info("Repo rx-get all thread id:  {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
        return rxBlocking(() -> dslFake.fakeList());
    }

    @Override
    public Single<Integer> rxCountAllDepartment() {
        logger.info("Repo rx-count thread id:  {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
        return rxBlocking(() -> dslFake.fakeCount());
    }
}
