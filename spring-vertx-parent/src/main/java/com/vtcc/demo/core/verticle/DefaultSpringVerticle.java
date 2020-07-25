package com.vtcc.demo.core.verticle;

import io.vertx.reactivex.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSpringVerticle extends AbstractSpringVerticle {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStart() {
        server = vertx.createHttpServer();
        router = Router.router(vertx);
        server.requestStream()
                .toFlowable()
                .subscribe(request -> router.accept(request));
        server.rxListen(9090).subscribe();
    }
}
