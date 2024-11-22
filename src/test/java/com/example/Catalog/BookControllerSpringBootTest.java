package com.example.Catalog;

import com.example.Catalog.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
public class BookControllerSpringBootTest {

    private RestClient restClient;

    public BookControllerSpringBootTest(@LocalServerPort int port) {
        restClient = RestClient.create("http://localhost:" + port);
    }

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


        restClient.post().uri("/api/create").body(book1).retrieve();
        restClient.post().uri("/api/create").body(book1).retrieve();
        restClient.post().uri("/api/create").body(book2).retrieve();
        restClient.post().uri("/api/create").body(book3).retrieve();
        restClient.post().uri("/api/create").body(book4).retrieve();
    }

    @Test
    void testSearchBooks() {
        ResponseEntity<List<Book>> response = restClient.get()
                .uri("/api/search?searchTerm=Spring")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Book>>() {});


        assertThat(response.getBody().get(0).getTitle()).isEqualTo("Spring Boot in Action");
        // assertThat(response.getBody().size()).isEqualTo(1); // Es werden genau 2 Ergebnisse erwartet
        //assertThat(response.getBody()).extracting(Book::getTitle)
        //.containsExactlyInAnyOrder("Spring Boot in Action"); // Die Titel der gefundenen Bücher
    }

}


