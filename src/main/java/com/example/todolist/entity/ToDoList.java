package com.example.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class ToDoList {  private Long id;
    private String contents;
    private String user;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ToDoList(String contents, String user, String password) {
        this.contents = contents;
        this.user = user;
        this.password = password;
    }

    public ToDoList(long id, String contents, String user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.contents = contents;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
