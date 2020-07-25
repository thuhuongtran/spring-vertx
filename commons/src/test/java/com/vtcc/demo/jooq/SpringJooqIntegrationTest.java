package com.vtcc.demo.jooq;

import com.vtcc.demo.core.config.vertx.binder.VertxRouteCenterBinder;
import com.vtcc.demo.core.verticle.DefaultSpringVerticle;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

import static com.vtcc.demo.commons.data.model.spring_vertx_demo.tables.Department.DEPARTMENT;

@SpringBootTest
@ComponentScan("com.vtcc.demo.commons")
public class SpringJooqIntegrationTest {

    @Autowired
    private DSLContext dsl;

    @Test
    public void getDepartments() {
        String url = "jdbc:mysql://localhost:3306/spring-vertx-demo";
        String username = "huongttt37";
        String password = "123456aA@";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    @Test
    public void getPackageName() {
        System.out.println(this.getClass().getPackage().getName());
    }

    @Test
    public void getClassName() {
        System.out.println(DefaultSpringVerticle.class.getName());
    }

    @Test
    public void insert() {
        System.out.println(dsl.insertInto(DEPARTMENT)
                .columns(DEPARTMENT.NAME)
                .values(DSL.value("telecom"))
                .execute());
    }
}
