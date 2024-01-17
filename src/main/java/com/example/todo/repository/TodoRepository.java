package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CouchbaseRepository<Todo, String> {

    List<Todo> findAllByUserId(String userId);

}
