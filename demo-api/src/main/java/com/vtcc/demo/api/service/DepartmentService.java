package com.vtcc.demo.api.service;

import com.vtcc.demo.commons.data.model.spring_vertx_demo.tables.pojos.Department;
import com.vtcc.demo.commons.repository.DepartmentRepository;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepo;

    public Single<List<Department>> getAllSortedDepartments() {
        departmentRepo.countAllDepartment();
        return departmentRepo.getAllDepartment();
    }

    public Single<Boolean> insertDepartment(String name) {
        return departmentRepo.insertDepartment(name);
    }

    public Single<List<Department>> rxGetAllSortedDepartments() {
        return departmentRepo.rxGetAllDepartment();
    }

    public Single<Integer> rxCountAllSortedDepartments() {
        return departmentRepo.rxCountAllDepartment();
    }
}
