package com.javenock.bookservice.advice;

import com.javenock.bookservice.exception.BookListEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BooksExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errorMap.put(error.getField(),error.getDefaultMessage());
        } );
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BookListEmptyException.class)
    public Map<String, String> emptyBookListHandler(BookListEmptyException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Book service error :", ex.getMessage());
        return errorMap;
    }



}
