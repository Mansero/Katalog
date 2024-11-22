package com.example.Catalog;

import com.example.Catalog.model.Book;
import com.example.Catalog.model.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        Book book1 = new Book();
        book1.setTitle("Spring Boot in Action");
        book1.setDescription("Comprehensive guide to Spring Boot");
        book1.setAuthor("Craig Walls");

        Book book2 = new Book();
        book2.setTitle("Java Persistence with Hibernate");
        book2.setDescription("Learn Hibernate for ORM");
        book2.setAuthor("Christian Bauer");

        Book book3 = new Book();
        book3.setTitle("Clean Code");
        book3.setDescription("Guide to writing clean code");
        book3.setAuthor("Robert C. Martin");

        Book book4 = new Book();
        book4.setTitle("14 Weeks of Java");
        book4.setDescription("A Novel Approach for Teaching Java");
        book4.setAuthor("Kaspar Riesen");

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
    }

    @Test
    void testFindByTitleContainingIgnoreCase () {
        List<Book> results = bookRepository.findByTitleContainingIgnoreCase("Java");
        assertEquals(2, results.size());
        assertEquals("Java Persistence with Hibernate", results.get(0).getTitle());
        assertEquals("14 Weeks of Java", results.get(1).getTitle());
    }

    @Test
   void testFindByDescriptionContainingIgnoreCase(){
        List<Book> results = bookRepository.findByDescriptionContainingIgnoreCase("ORM");
        assertEquals(1, results.size());
        assertEquals("Java Persistence with Hibernate", results.get(0).getTitle());
    }

    @Test
    void testFindByAuthorContainingIgnoreCase(){
        List<Book> results = bookRepository.findByAuthorContainingIgnoreCase("Martin");
        assertEquals(1, results.size());
        assertEquals("Clean Code", results.get(0).getTitle());
    }

    @Test
    void testSearchBooksWithoutSearchTerm(){
        List<Book> results = bookRepository.searchBooks("");
        assertEquals(4, results.size());
        assertEquals("14 Weeks of Java", results.get(3).getTitle());
    }

    @Test
    void testSearchBooksWithFalseSearchTerm(){
        List<Book> results = bookRepository.searchBooks("J.K.Rowling");
        assertEquals(0, results.size());
    }


}
