package com.vtcc.demo.commons.config.database.jooq;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DatasourceConfig extends HikariConfig {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public HikariDataSource dataSource() {
        logger.info("Datasource: {}, username: {}", this.getJdbcUrl(), this.getUsername());
        return new HikariDataSource(this);
    }
}
