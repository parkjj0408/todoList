package com.example.todolist.dto;

import com.example.todolist.entity.ToDoList;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Generated;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter

public class ResponseDto {
    private Long id;
    private String contents;
    private String user;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime createdAt;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime updatedAt;

    public ResponseDto(ToDoList todoList) {
        this.id = todoList.getId();
        this.contents = todoList.getContents();
        this.user = todoList.getUser();
        this.createdAt = todoList.getCreatedAt();
        this.updatedAt = todoList.getUpdatedAt();
    }

    public ResponseDto(final Long id, final String contents, final String user, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.contents = contents;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}


