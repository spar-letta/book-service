package com.javenock.bookservice.service;

import com.javenock.bookservice.exception.BookListEmptyException;
import com.javenock.bookservice.model.Book;
import com.javenock.bookservice.repository.BookRepository;
import com.javenock.bookservice.request.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Book save_new_book(BookRequest bookRequest) {
        Book book = Book.builder()
                .book_title(bookRequest.getBook_title())
                .date_published(LocalDate.parse(bookRequest.getDate_published()))
                .authorid(bookRequest.getAuthorid())
                .build();
       return bookRepository.save(book);
    }


    public List<Book> listOfBooks() throws BookListEmptyException {
        List<Book> list_books = bookRepository.findAll();
        if(list_books.size() > 0){
            return list_books;
        }else{
            throw new BookListEmptyException("Book list is empty");
        }
    }

    public List<Book> getBooksByAuthorId(Long authorid) {
        List<Book> booksByAuthorId = bookRepository.getByAuthorid(authorid);
        return booksByAuthorId;
    }
}
