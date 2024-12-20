package com.example.Catalog;

import com.example.Catalog.enums.GenreEnum;
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
        bookRepository.deleteAll();

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

        Book book3 = new Book();
        book3.setIsbn("259948000-0");
        book3.setPages(254);
        book3.setGenre(GenreEnum.FANTASY);
        book3.setPrice(59.86);;
        book3.setTitle("Clean Code");
        book3.setDescription("Guide to writing clean code");
        book3.setAuthor("Robert C. Martin");

        Book book4 = new Book();
        book4.setIsbn("064228017-7");
        book4.setPages(656);
        book4.setGenre(GenreEnum.GUIDE);
        book4.setPrice(79.22);;
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
