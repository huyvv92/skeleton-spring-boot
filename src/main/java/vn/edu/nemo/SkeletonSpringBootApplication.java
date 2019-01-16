package vn.edu.nemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SkeletonSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkeletonSpringBootApplication.class, args);
    }
}
