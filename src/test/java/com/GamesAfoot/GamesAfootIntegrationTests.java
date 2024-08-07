package com.GamesAfoot;

import com.GamesAfoot.models.Hunt;
import com.GamesAfoot.models.Progress;
import com.GamesAfoot.models.Location;
import com.GamesAfoot.repositories.HuntRepository;
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

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class GamesAfootIntegrationTests {

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    private final static String PROGRESS_URL = "/progress";
    private final static String HUNT_URL = "/hunts";

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

    @Autowired
    private HuntRepository huntRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        progressRepository.deleteAll();
    }

    @Test
    void testCreateHunt() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(
                        """
                        {
                            "startLatitude": "47.6061",
                            "startLongitude": "122.3328",
                            "distance": "3",
                            "numSites": "5",
                            "gameType": "Historic"
                        }
                        """
                )
                .when()
                .post(HUNT_URL)
                .then()
                .statusCode(201)
                .body("startLatitude", is("47.6061"))
                .body("startLongitude", is("122.3328"))
                .body("distance", is("3"))
                .body("numSites", is("5"))
                .body("gameType", is("Historic"));

    }

    @Test
    void testGenerateLocations() throws Exception {
        List<Location> generatedLocations = new ArrayList<>();
        Hunt hunt = new Hunt("47.6061", "122.3328", "2", "3", "Yummy treats", generatedLocations);
        huntRepository.save(hunt);

        given()
                .contentType(ContentType.TEXT)
                .when()
                .post(HUNT_URL + "/{id}/generate_locations", hunt.getId())
                .then()
                .log()
                .body()
                .statusCode(201);
    }


    @Test
    void shouldGetAllProgress() throws Exception {
        List<Progress> progressList = List.of(
                new Progress(null, 2, 1, 0, false),
                new Progress(null, 1, 2, 3, true)
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

    @Test
    void testCreateProgress() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(
                    """
                    {
                        "userId": 1,
                        "huntId": 3,
                        "targetLocationIndex": 0,
                        "gameComplete": false
                    }
                    """
                )
                .when()
                .post(PROGRESS_URL)
                .then()
                .statusCode(201)
                .body("userId", is(1))
                .body("huntId", is(3))
                .body("targetLocationIndex", is(0))
                .body("gameComplete", is(false));

    }


    @Test
    public void testGetProgressById() throws Exception {
        Progress progress = progressRepository.save(new Progress(null, 1, 2, 3, true));
        assertThat(progressRepository.findById(progress.getId())).isPresent();

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(PROGRESS_URL + "/{id}", progress.getId())
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("userId", is(1))
                .body("huntId", is(2))
                .body("targetLocationIndex", is(3))
                .body("gameComplete", is(true));
    }

}
