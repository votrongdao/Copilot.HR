package bbv.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Entry Point for Spring Boot People HR Application.
 * Placed in root package 'bbv.hr' for automatic Component, Entity, and JPA Repository scanning.
 */
@SpringBootApplication
public class PeopleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeopleApplication.class, args);
    }

}
