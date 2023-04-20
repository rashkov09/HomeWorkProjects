package com.slm.springlibrarymanagement.configuration;

import com.slm.springlibrarymanagement.mappers.AuthorMapper;
import com.slm.springlibrarymanagement.mappers.BookMapper;
import com.slm.springlibrarymanagement.mappers.ClientMapper;
import com.slm.springlibrarymanagement.mappers.OrderMapper;
import com.slm.springlibrarymanagement.util.CustomDateFormatter;
import com.slm.springlibrarymanagement.util.InputValidator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Scanner;

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
    public AuthorMapper authorRowMapper() {
        return new AuthorMapper();
    }

    @Bean
    public BookMapper bookRowMapper() {
        return new BookMapper();
    }

    @Bean
    public ClientMapper clientRowMapper() {
        return new ClientMapper();
    }

    @Bean
    public OrderMapper orderRowMapper() {
        return new OrderMapper();
    }

    @Bean
    public StringBuilder builder() {
        return new StringBuilder();
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }


}
