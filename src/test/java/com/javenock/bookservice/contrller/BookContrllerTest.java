package com.javenock.bookservice.contrller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javenock.bookservice.exception.BookListEmptyException;
import com.javenock.bookservice.model.Book;
import com.javenock.bookservice.request.BookRequest;
import com.javenock.bookservice.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;

@WebMvcTest
public class BookContrllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("save_book")
    public void shouldSaveBook() throws Exception {
        BookRequest bkRequest = new BookRequest();
        bkRequest.setBook_title("My cat");
        bkRequest.setDate_published("2010-02-12");
        bkRequest.setAuthorid(1L);
        Book newBook = Book.builder()
                        .book_title(bkRequest.getBook_title())
                                .date_published(LocalDate.parse(bkRequest.getDate_published()))
                                        .authorid(bkRequest.getAuthorid()).build();


        when(bookService.save_new_book(any(BookRequest.class))).thenReturn(newBook);
        this.mockMvc.perform(post("/book/create-book").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(bkRequest))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.book_title").value(equalTo(newBook.getBook_title())));
    }

    @Test
    @DisplayName("ListAllBooks")
    public void shouldReturnAllSavedBooks() throws Exception {
        Book bk = new Book();
        bk.setBook_title("My cat");
        bk.setDate_published(LocalDate.parse("2010-02-12"));
        bk.setAuthorid(1L);

        Book bk2 = new Book();
        bk2.setBook_title("The Wide River");
        bk2.setDate_published(LocalDate.parse("2018-02-10"));
        bk2.setAuthorid(2L);

        List<Book> bkList = new ArrayList<>();
        bkList.add(bk);
        bkList.add(bk2);

        when(bookService.listOfBooks()).thenReturn(bkList);
        this.mockMvc.perform(get("/book"))
                        .andExpect(status().isOk())
                                .andExpect(jsonPath("$.size()").value(equalTo(bkList.size())));
    }

 @Test
    @DisplayName("BookByAuthorId")
    public void shouldFindBookByAuthorId() throws Exception {
     Book bk = new Book();
     bk.setBookid(1L);
     bk.setBook_title("My cat");
     bk.setDate_published(LocalDate.parse("2010-02-12"));
     bk.setAuthorid(1L);

     Book bk2 = new Book();
     bk2.setBookid(2L);
     bk2.setBook_title("The Wide River");
     bk2.setDate_published(LocalDate.parse("2018-02-10"));
     bk2.setAuthorid(1L);

     List<Book> bkList = new ArrayList<>();
     bkList.add(bk);
     bkList.add(bk2);

     when(bookService.getBooksByAuthorId(anyLong())).thenReturn(bkList);
     this.mockMvc.perform(get("/book/author_id/{authorid}",1L))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.size()").value(equalTo(bkList.size())));
 }

}
