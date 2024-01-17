package com.example.todo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@Document
public class User {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.USE_ATTRIBUTES, delimiter = "#")
    private Long id;

    @Field
    private String name;

    @Field
    private String lastname;

    @Field
    private String username;

    @Field
    private String email;

    @Field
    private String password;

    @Field
    private Set<String> roles;

    @Field
    private LocalDateTime createDate;

    @Field
    private LocalDateTime updateDate;
}
