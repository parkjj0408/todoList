package com.example.todolist.service;

import com.example.todolist.dto.RequestDto;
import com.example.todolist.dto.ResponseDto;
import java.util.List;

public interface ToDoService {

        ResponseDto saveTodo(RequestDto dto);

        List<ResponseDto> findAllTodo();

        ResponseDto findTodoById(Long id);

        ResponseDto updateTodoList(Long id, RequestDto dto);

        void deleteList(Long id);
    }


