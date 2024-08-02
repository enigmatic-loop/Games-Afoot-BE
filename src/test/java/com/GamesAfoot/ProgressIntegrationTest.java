package com.GamesAfoot;

import com.GamesAfoot.controllers.ProgressController;
import com.GamesAfoot.models.Progress;
import com.GamesAfoot.repositories.ProgressRepository;
import com.GamesAfoot.services.ProgressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProgressIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProgressRepository progressRepository;

    @Mock
    private ProgressService progressService;

    @InjectMocks
    private ProgressController progressController;

    private Progress progress;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String urlWithPort = "http://localhost:" + port + "/progress";


}
