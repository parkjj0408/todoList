package com.example.todolist.repository;

import com.example.todolist.dto.ResponseDto;
import com.example.todolist.entity.ToDoList;

import java.util.List;
import java.util.Optional;

public interface ToDoRepository {

        ResponseDto saveTodo(ToDoList toDoList);

        List<ResponseDto> findAllTodo();

        Optional<ToDoList> findTodoById(Long id);

        Optional<ToDoList> findTodoByIdForUpdate(Long id);

        int updateTodoList(Long id, String contents, String user, String password);

        void deleteList(Long id);
    }

