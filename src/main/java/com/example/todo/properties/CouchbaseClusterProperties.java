package com.example.todo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "couchbase")
public class CouchbaseClusterProperties {

    private String connectionString;
    private String userName;
    private String password;

    @NestedConfigurationProperty
    private CouchbaseBucketProperties bucketTodo;

    @NestedConfigurationProperty
    private CouchbaseBucketProperties bucketUser;
}
