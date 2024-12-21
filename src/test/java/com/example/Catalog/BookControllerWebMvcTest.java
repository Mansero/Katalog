package com.example.Catalog;

import com.example.Catalog.enums.GenreEnum;
import com.example.Catalog.model.Book;
import com.example.Catalog.model.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

        @Test
        public void searchBooksTest() throws Exception {
            //Create Books
            Book book1 = new Book();
            book1.setIsbn("784827138-2");
            book1.setPages(938);
            book1.setGenre(GenreEnum.THRILLER);
            book1.setPrice(74.95);;
            book1.setTitle("Spring Boot in Action");
            book1.setDescription("Comprehensive guide to Spring Boot");
            book1.setAuthor("Craig Walls");

            Book book2 = new Book();
            book2.setIsbn("949300256-X");
            book2.setPages(726);
            book2.setGenre(GenreEnum.NOVAL);
            book2.setPrice(77.45);;
            book2.setTitle("Java Persistence with Hibernate");
            book2.setDescription("Learn Hibernate for ORM");
            book2.setAuthor("Christian Bauer");

            //Add Books (in FakeRepository)
            Mockito.when(bookRepository.searchBooks("Spring")).thenReturn(Arrays.asList(book1));

              //Test Search-Method
            mockMvc.perform(MockMvcRequestBuilders.get("/api/books/search?searchTerm=Spring")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].title", is("Spring Boot in Action")));

        }
}
