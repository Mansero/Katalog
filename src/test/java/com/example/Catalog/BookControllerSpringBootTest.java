package com.example.Catalog;

import com.example.Catalog.enums.GenreEnum;
import com.example.Catalog.model.Book;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class BookControllerSpringBootTest {

    private RestClient restClient;

    public BookControllerSpringBootTest(@LocalServerPort int port) {
        restClient = RestClient.create("http://localhost:" + port + "/api/");

    }

    @Test
    void testSearchBooks() throws Exception {
        // Create test books
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

        // Add books via POST requests
        addBook(book1);
        addBook(book2);
        addBook(book3);

        // Perform search request
        var response = restClient.get()
                .uri("books/search?searchTerm=Spring")
                .retrieve()
                .toEntity(Book[].class);

        // Verify response using AssertJ
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull().hasSize(1);
        assertThat(response.getBody()[0].getTitle()).isEqualTo("Spring Boot in Action");
    }

    private void addBook(Book book) throws Exception {
        var request = restClient.post()
                .uri("books")
                .contentType(APPLICATION_JSON)
                .body(book)
                .retrieve();
        assertThat(request).isNotNull();
        assertThat(request.toBodilessEntity().getStatusCode()).isEqualTo(CREATED);
    }
}


