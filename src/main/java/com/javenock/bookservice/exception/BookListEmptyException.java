package com.javenock.bookservice.exception;

public class BookListEmptyException extends Exception{
    public BookListEmptyException(String message) {
        super(message);
    }
}
