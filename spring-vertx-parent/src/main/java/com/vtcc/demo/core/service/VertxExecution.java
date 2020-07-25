package com.vtcc.demo.core.service;

import com.vtcc.demo.core.config.vertx.VertxWrapper;
import com.vtcc.demo.core.extension.functional.SupplierThrowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertxExecution {
    private static Logger logger = LoggerFactory.getLogger(VertxExecution.class);
    public static <T> Single<T> rxBlocking(T result) {
        return VertxWrapper.vertx().rxExecuteBlocking(future -> {
            try {
                logger.info("vertx - thread id:  {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
                future.complete(result);
            } catch (Exception e) {
                logger.info(e.getMessage());
                future.fail(e);
            }
        });
    }

    public static <T> Single<T> rxBlocking(SupplierThrowable<T> resultSupplier) {
        return VertxWrapper.vertx().rxExecuteBlocking(future -> {
            try {
                logger.info("vertx rx- thread id: {}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
                future.complete(resultSupplier.get());
            } catch (Exception e) {
                future.fail(e);
            }
        });
    }
}
