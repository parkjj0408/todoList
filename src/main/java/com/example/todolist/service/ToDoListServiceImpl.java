package com.example.todolist.service;

import com.example.todolist.dto.RequestDto;
import com.example.todolist.dto.ResponseDto;
import com.example.todolist.entity.ToDoList;
import com.example.todolist.repository.ToDoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoListServiceImpl implements ToDoService {
    private final ToDoRepository todoRepository;

    private ToDoListServiceImpl(ToDoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public ResponseDto saveTodo(RequestDto dto) {
        ToDoList toDoList = new ToDoList(dto.getContents(), dto.getUser(), dto.getPassword());
        return this.todoRepository.saveTodo(toDoList);
    }

    public List<ResponseDto> findAllTodo() {
        return this.todoRepository.findAllTodo();
    }

    public ResponseDto findTodoById(Long id) {
        Optional<ToDoList> optionalList = this.todoRepository.findTodoById(id);
        if (optionalList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id" + id);
        } else {
            return new ResponseDto((ToDoList)optionalList.get());
        }
    }

    public ResponseDto updateTodoList(Long id, RequestDto dto) {
        ToDoList toDoList = (ToDoList)this.todoRepository.findTodoByIdForUpdate(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id " + id));
        if (!toDoList.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        } else {
            if (toDoList.getContents() != null) {
                toDoList.setContents(dto.getContents());
            }

            if (toDoList.getUser() != null) {
                toDoList.setUser(dto.getUser());
            }

            toDoList.setUpdatedAt(LocalDateTime.now());
            return new ResponseDto(toDoList);
        }
    }

    public void deleteList(Long id) {
        Optional<ToDoList> toDo = this.todoRepository.findTodoById(id);
        if (toDo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 리스트가 존재하지 않습니다.");
        } else {
            this.todoRepository.deleteList(id);
        }
    }
}

