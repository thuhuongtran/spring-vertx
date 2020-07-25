package com.vtcc.demo.core.config.vertx;


import io.vertx.reactivex.core.Vertx;

public class VertxWrapper {
    private static Vertx VERTX = Vertx.vertx();

    public static Vertx vertx() {
        return VERTX;
    }
}
