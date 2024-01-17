package com.example.todo.model;

import lombok.Getter;

@Getter
public enum TodoStatus {

    IN_PROGRESS(1),
    DONE(2);

    final int statusId;

    TodoStatus(int statusId) {
        this.statusId = statusId;
    }

}
