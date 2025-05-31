package org.ea.cinevibe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CinevibeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinevibeApplication.class, args);
    }

}
