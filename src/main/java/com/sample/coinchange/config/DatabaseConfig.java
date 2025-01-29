package com.sample.coinchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    /*
    @Bean(name = "database1DataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.database1")
    public DataSource database1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "database2DataSource")
    @ConfigurationProperties("spring.datasource.database2")
    public DataSource database2DataSource() {
        return DataSourceBuilder.create().build();
    }
    */
}
