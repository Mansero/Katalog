package com.example.Catalog.controllers;

import com.example.Catalog.model.Book;
import com.example.Catalog.model.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("api/all")
    List<Book> findAll(){
        return bookRepository.findAll();
    }

    @GetMapping("api/search")
    List<Book> searchBooks(String searchTerm){
    return bookRepository.searchBooks(searchTerm);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createBook(@RequestBody Book book) {
        bookRepository.save(book);
    }
}


