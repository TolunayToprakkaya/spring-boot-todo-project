package com.example.todo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class CouchbaseBucketProperties {

    private String name;
    private String userName;
    private String password;
}
