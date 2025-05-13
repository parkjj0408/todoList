package com.example.todolist.dto;

import lombok.Generated;
import lombok.Getter;

@Getter
    public class RequestDto {
        private String contents;
        private String user;
        private String password;



        public RequestDto(final String contents, final String user, final String password) {
            this.contents = contents;
            this.user = user;
            this.password = password;
        }
    }

