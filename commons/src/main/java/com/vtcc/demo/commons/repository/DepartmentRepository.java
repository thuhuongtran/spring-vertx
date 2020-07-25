package com.vtcc.demo.commons.repository;

import com.vtcc.demo.commons.data.model.spring_vertx_demo.tables.pojos.Department;
import io.reactivex.Single;

import java.util.List;

public interface DepartmentRepository {
    Single<List<Department>> getAllDepartment();

    Single<Boolean> insertDepartment(String name);

    Single<List<Department>> rxGetAllDepartment();

    Single<Integer> countAllDepartment();

    Single<Integer> rxCountAllDepartment();
}
