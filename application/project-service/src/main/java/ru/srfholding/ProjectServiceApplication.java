package ru.srfholding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.srfholding.trackermodels.repository")
@EntityScan(basePackages = "ru.srfholding.trackermodels.model")
public class ProjectServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectServiceApplication.class);
    }
}