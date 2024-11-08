package com.example.Catalog.controllers;

import com.example.Catalog.model.Book;
import com.example.Catalog.model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("books")
@Controller
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/all")
    public String getAllBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/search")
    public String searchBooks(Model model, String searchTerm) {
        List<Book> books = bookRepository.searchBooks(searchTerm);
        model.addAttribute("books", books);
        return "bookSearch";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createBook(@RequestBody Book book) {
        bookRepository.save(book);
    }
}


