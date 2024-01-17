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
import java.util.List;

@Getter
@Setter
@Builder
@Document
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.USE_ATTRIBUTES, delimiter = "#")
    private Long id;

    @Field
    private String name;

    @Field
    private List<String> categoryList;

    @Field
    private TodoStatus todoStatus;

    @Field
    private String userId;

    @Field
    private LocalDateTime createDate;

    @Field
    private LocalDateTime updateDate;

}
