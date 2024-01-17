package com.example.todo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Todo Application"))
public class TodoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoProjectApplication.class, args);
	}

}
