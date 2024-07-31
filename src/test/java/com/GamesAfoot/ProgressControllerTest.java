package com.GamesAfoot;

import com.GamesAfoot.Models.Progress;
import com.GamesAfoot.Controllers.ProgressController;
import com.GamesAfoot.Repositories.ProgressRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProgressControllerTest {
    private final static String PROGRESS_URL = "/progress";

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProgressRepository progressRepository;

    @InjectMocks
    private ProgressController progressController;

    private Progress progress;

    @BeforeEach
    public void init() {
        progress = new Progress(1, 1, 1, 0, false);
    }

    // GET route tests
    @Test
    public void getAllProgressReturns200() throws Exception {
        mockMvc.perform(get(PROGRESS_URL)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllProgressContainsObject() throws Exception {
        mockMvc.perform(get(PROGRESS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].userId").exists())
                .andExpect(jsonPath("$[0].huntId").exists())
                .andExpect(jsonPath("$[0].targetLocationIndex").exists())
                .andExpect(jsonPath("$[0].gameComplete").exists());
    }

    @Test
    public void getAllProgressIsNotNull() throws Exception {
        given(progressRepository.findAll())
                .willReturn(List.of(progress));
        Iterable<Progress> progressList = progressController.getAllProgress();

        assertThat(progressList).isNotNull();
    }

    // POST route tests
    @Test
    public void postProgressReturns201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(PROGRESS_URL)
                .content(objectMapper.writeValueAsString(progress))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.huntId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetLocationIndex").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameComplete").exists());
    }

}
