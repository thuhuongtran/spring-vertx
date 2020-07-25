package com.vtcc.demo.core.config.vertx.deploy;

import com.vtcc.demo.core.config.vertx.VertxWrapper;
import com.vtcc.demo.core.config.vertx.deploy.event.VerticleUpEvent;
import com.vtcc.demo.core.verticle.DefaultSpringVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class VertxDeployer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ApplicationContext staticAppContext;
    @Autowired
    private ApplicationContext appContext;

    @PostConstruct
    public void deploy() {
        logger.info("Vertx is deploying on port 9090 ...");
        setAppContext(appContext);
        VertxWrapper.vertx()
                .rxDeployVerticle(DefaultSpringVerticle.class.getName())
                .subscribe();
//                .doOnSuccess(id -> {
//                    logger.info("Verticle is deployed with id {}", id);

                    // Publish event Verticle is up if success
//                    appContext.publishEvent(new VerticleUpEvent(this));
//                });
    }

    private static void setAppContext(ApplicationContext appContext) {
        if (staticAppContext == null) {
            staticAppContext = appContext;
        }
    }

    public static ApplicationContext getStaticAppContext() {
        return staticAppContext;
    }
}
