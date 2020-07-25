package com.vtcc.demo.api.controller;

import com.vtcc.demo.api.config.vertx.model.DefaultResponse;
import com.vtcc.demo.api.data.request.DepartmentRequest;
import com.vtcc.demo.api.service.DepartmentService;
import com.vtcc.demo.commons.data.model.spring_vertx_demo.tables.pojos.Department;
import com.vtcc.demo.core.config.annotation.http.VertxGet;
import com.vtcc.demo.core.config.annotation.http.VertxPost;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.vtcc.demo.commons.constant.ResponseMessageConstant.FAILURE;
import static com.vtcc.demo.commons.constant.ResponseMessageConstant.SUCCESS;

@Controller
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DepartmentService departmentService;

    @VertxGet("/departments")
    public Single<ResponseEntity<DefaultResponse<List<Department>>>> getAll() {
        return departmentService.getAllSortedDepartments()
                .map(DefaultResponse::okEntity);
    }

    @VertxGet("/rx-department")
    public Single<ResponseEntity<DefaultResponse<List<Department>>>> rxGetAll() {
        departmentService.rxCountAllSortedDepartments();
        return departmentService.rxGetAllSortedDepartments()
                .map(DefaultResponse::okEntity);
    }

    @VertxGet("/rx-count")
    public Single<ResponseEntity<DefaultResponse<Integer>>> rxCountAll() {
        departmentService.rxGetAllSortedDepartments();
        return departmentService.rxCountAllSortedDepartments()
                .map(DefaultResponse::okEntity);
    }

    @VertxPost("/department/create")
    public Single<ResponseEntity<DefaultResponse<String>>> create(@RequestBody DepartmentRequest request) {
        logger.info("name: {}", request.getName());
        return departmentService.insertDepartment(request.getName())
                .map(isSuccess -> isSuccess ?
                        DefaultResponse.okEntity(SUCCESS) : DefaultResponse.okEntity(FAILURE));
    }
}
