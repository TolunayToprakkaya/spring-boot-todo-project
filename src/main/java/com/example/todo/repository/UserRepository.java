package com.example.todo.repository;

import com.example.todo.model.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CouchbaseRepository<User, String> {

    Optional<User> findByUsername(String username);
}
