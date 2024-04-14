package com.example.ecommerce.infrastructure;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories("com.example.ecommerce.ports")
@EntityScan("com.example.ecommerce.domain")
@Slf4j
@ComponentScan(basePackages = {"com.example.ecommerce"})
public class InditexAdapter {

    @Autowired
    private TestDataConfigService testDataConfigService;

    public static void main(String[] args) {
        SpringApplication.run(InditexAdapter.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(InditexAdapter::shutdown));
        log.info("===================== Starting InditexAdaptator ========================");
    }

    private static void shutdown() {
        log.info("===================== InditexAdaptator Ended ========================");
    }

    @PostConstruct
    private void initDb() {
        log.info("****** Inserting Data Testing ******");
        testDataConfigService.insertarDatosPrueba();
        log.info("****** Inserted Data Successfully ******");
    }
}