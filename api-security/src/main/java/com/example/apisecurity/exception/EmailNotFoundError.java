package com.example.apisecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailNotFoundError extends ResponseStatusException {
    public EmailNotFoundError (){
        super(HttpStatus.BAD_REQUEST,"Email not found!");
    }
}
