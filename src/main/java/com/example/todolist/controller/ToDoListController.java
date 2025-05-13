package com.example.todolist.controller;

import com.example.todolist.dto.RequestDto;
import com.example.todolist.dto.ResponseDto;
import com.example.todolist.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping({"/todos"})
public class ToDoListController {

        private final ToDoService toDoService;

        private ToDoListController(ToDoService toDoService) {
            this.toDoService = toDoService;
        }

        @PostMapping
        public ResponseEntity<ResponseDto> saveTodo(@RequestBody RequestDto dto) {
            return new ResponseEntity(this.toDoService.saveTodo(dto), HttpStatus.CREATED);
        }

        @GetMapping
        public List<ResponseDto> findAllTodo() {
            return this.toDoService.findAllTodo();
        }

        @GetMapping({"/{id}"})
        public ResponseEntity<ResponseDto> findTodoById(@PathVariable Long id) {
            return new ResponseEntity(this.toDoService.findTodoById(id), HttpStatus.OK);
        }

        @PatchMapping({"/{id}"})
        public ResponseEntity<ResponseDto> updateList(@PathVariable Long id, @RequestBody RequestDto dto) {
            return new ResponseEntity(this.toDoService.updateTodoList(id, dto), HttpStatus.OK);
        }

        @DeleteMapping({"/{id}"})
        public ResponseEntity<Void> deleteList(@PathVariable Long id) {
            this.toDoService.deleteList(id);
            return ResponseEntity.noContent().build();
        }
    }

