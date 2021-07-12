package org.techfrog.fileprocessingservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "File processing API"))
public class FileProcessingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileProcessingServiceApplication.class, args);
    }

}
