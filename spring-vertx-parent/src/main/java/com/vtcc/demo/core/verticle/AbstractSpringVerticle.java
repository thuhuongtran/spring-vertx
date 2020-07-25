package com.vtcc.demo.core.verticle;

import com.vtcc.demo.core.config.vertx.binder.VertxRouteCenterBinder;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.vtcc.demo.core.config.vertx.deploy.VertxDeployer.getStaticAppContext;

public abstract class AbstractSpringVerticle extends AbstractVerticle {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public HttpServer server;
    public Router router;

    public abstract void onStart();

    @Override
    public void start() throws Exception {
        super.start();
        onStart();
        logger.info("Starting binding vertx routes ...");
        registerRoutes();
    }

    private void registerRoutes() {
        VertxRouteCenterBinder routeCenterBinder = getStaticAppContext().getBean(VertxRouteCenterBinder.class);
        routeCenterBinder.bind(router);
    }
}
