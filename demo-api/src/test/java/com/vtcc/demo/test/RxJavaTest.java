package com.vtcc.demo.test;

import com.vtcc.demo.commons.repository.DepartmentRepository;
import com.vtcc.demo.commons.repository.impl.DepartmentRepositoryImpl;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class RxJavaTest {

    private DepartmentRepository departmentRepo;

    @Test
    public void rxWithSupplierFunctionalInterface() {
        departmentRepo.rxGetAllDepartment();
        departmentRepo.rxCountAllDepartment();
    }

    @Test
    public void rxWithoutSupplierFunctionalInterface() {
        departmentRepo.getAllDepartment();
        departmentRepo.countAllDepartment();
    }

    @Test
    public void singleAndObservable() throws InterruptedException {
        System.out.println("Main: " + Thread.currentThread().getId() + "-" + Thread.currentThread().getName());
        Single.timer(1, TimeUnit.SECONDS)
                .subscribe(time -> System.out.println(Thread.currentThread().getName()));
        Thread.sleep(1500);
    }

    @Test
    public void singleZip() {
        List<Single<Integer>> observables =
                new ArrayList<Single<Integer>>();
        observables.add(Single.just(1));
        observables.add(Single.just( 3));

//        Observable.zip(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Integer::sum)
//                .subscribe(e -> System.out.println(e));

        Observable.zip(
                Observable.fromArray("Simple", "Moderate", "Complex").map(str -> {
                    System.out.println("Observa 1 "+Thread.currentThread().getName());
                    return str.toUpperCase();
                }).subscribeOn(Schedulers.newThread()),
                Observable.fromArray("Solutions", "Success", "Hierarchy").map(str -> {
                    System.out.println("Observa 2 "+Thread.currentThread().getName());
                    return str.toUpperCase();
                }).subscribeOn(Schedulers.newThread()),
                (str1, str2) -> str1 + " " + str2).subscribe(e -> System.out.println(e+"-"+Thread.currentThread().getName()));
    }
}
