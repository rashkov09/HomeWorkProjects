package com.slm.springlibrarymanagement.configuration;

import com.slm.springlibrarymanagement.mappers.AuthorRowMapper;
import com.slm.springlibrarymanagement.mappers.BookRowMapper;
import com.slm.springlibrarymanagement.mappers.ClientRowMapper;
import com.slm.springlibrarymanagement.mappers.OrderRowMapper;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import com.slm.springlibrarymanagement.util.InputValidator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    public InputValidator nameValidator() {
        return new InputValidator();
    }

    @Bean
    public CustomDateFormatter dateFormatter() {
        return new CustomDateFormatter();
    }


    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("dobrin");
        config.setPassword("dobrin12345");
        config.setSchema("slm");
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaximumPoolSize(20);
        config.setMaxLifetime(1800000);
        config.setMinimumIdle(5);
        return new HikariDataSource(config);
    }

    @Bean
    public AuthorRowMapper authorRowMapper() {
        return new AuthorRowMapper();
    }

    @Bean
    public BookRowMapper bookRowMapper() {
        return new BookRowMapper();
    }

    @Bean
    public ClientRowMapper clientRowMapper() {
        return new ClientRowMapper();
    }

    @Bean
    public OrderRowMapper orderRowMapper() {
        return new OrderRowMapper();
    }

    @Bean
    public StringBuilder builder(){
        return new StringBuilder();
    }


}
