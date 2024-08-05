package com.GamesAfoot;

import com.GamesAfoot.models.Progress;
import com.GamesAfoot.repositories.ProgressRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.hamcrest.Matchers.hasSize;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ProgressIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    private final static String PROGRESS_URL = "/progress";

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProgressRepository progressRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        progressRepository.deleteAll();
    }

    @Test
    void shouldGetAllProgress() throws Exception {
        List<Progress> progressList = List.of(
                new Progress(1, 1, 1, 0, false),
                new Progress(2, 1, 2, 3, true)
        );
        progressRepository.saveAll(progressList);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(PROGRESS_URL)
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }

//    @Test
//    void testCreateProgress() throws Exception {
//        Progress progress = new Progress(1, 1, 1, 0, false);
//
//        mockMvc.perform(post(PROGRESS_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(progress)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.userId", is(1)))
//                .andExpect(jsonPath("$.huntId", is(1)))
//                .andExpect(jsonPath("$.targetLocationIndex", is(0)))
//                .andExpect(jsonPath("$.gameComplete", is(false)))
//                .andExpect(jsonPath("$").isNotEmpty());
//
//    }
//
//    @Test
//    public void testGetAllProgress() throws Exception {
//        mockMvc.perform(get(PROGRESS_URL))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].id").exists())
//                .andExpect(jsonPath("$[0].userId").exists())
//                .andExpect(jsonPath("$[0].huntId").exists())
//                .andExpect(jsonPath("$[0].targetLocationIndex").exists())
//                .andExpect(jsonPath("$[0].gameComplete").exists());
//    }
//
//    @Test
//    public void testGetProgressById() throws Exception {
//        mockMvc.perform(get(PROGRESS_URL + "/1"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.userId", is(1)))
//                .andExpect(jsonPath("$.huntId", is(1)))
//                .andExpect(jsonPath("$.targetLocationIndex", is(3)))
//                .andExpect(jsonPath("$.gameComplete", is(false)))
//                .andExpect(jsonPath("$").isNotEmpty());
//    }

}
