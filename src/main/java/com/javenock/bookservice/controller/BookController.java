package com.javenock.bookservice.controller;

import com.javenock.bookservice.exception.BookListEmptyException;
import com.javenock.bookservice.model.Book;
import com.javenock.bookservice.request.BookRequest;
import com.javenock.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("create-book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book save_new_book(@RequestBody @Valid BookRequest bookRequest){
        return bookService.save_new_book(bookRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> fetchAllBooks() throws BookListEmptyException {
        return bookService.listOfBooks();
    }

    @GetMapping("/author_id/{authorid}")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> booksByAuthorId(@PathVariable Long authorid){
        return bookService.getBooksByAuthorId(authorid);
    }
}
