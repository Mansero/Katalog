package com.example.Catalog.controllers;

import com.example.Catalog.model.Book;
import com.example.Catalog.model.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/all")
    List<Book> findAll(){
        return bookRepository.findAll();
    }

    @GetMapping("/search")
    List<Book> searchBooks(String searchTerm){
    return bookRepository.searchBooks(searchTerm);
    }

    @GetMapping("/{isbn}")
    ResponseEntity<Book> get(@PathVariable String isbn) {
        return bookRepository.findById(isbn)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createBook(@RequestBody Book book) {
        bookRepository.save(book);
    }
}


