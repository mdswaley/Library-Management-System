package org.example.librarymanagementsystem.Controller;

import org.example.librarymanagementsystem.DTOs.BooksDTO;
import org.example.librarymanagementsystem.Entity.BooksEntity;
import org.example.librarymanagementsystem.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@Import(TestContainerConfig.class)
@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {

    @Autowired
    public WebTestClient webTestClient;

    BooksEntity testBookClass = BooksEntity.builder()
            .title("Go Set a Watchman")
            .author("Harper Lee")
            .isbn("145323453")
            .AvailableQuantity(30)
            .IssueQuantity(0)
            .build();

    BooksDTO testBookDto = BooksDTO.builder()
            .id(1L)
            .title("Go Set a Watchman")
            .author("Harper Lee")
            .isbn("145323453")
            .AvailableQuantity(30)
            .IssueQuantity(0)
            .build();

}
