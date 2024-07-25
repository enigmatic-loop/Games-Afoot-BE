package com.GamesAfoot.GamesAfoot;

import com.GamesAfoot.GamesAfoot.Progress.Progress;
import com.GamesAfoot.GamesAfoot.Progress.ProgressController;
import com.GamesAfoot.GamesAfoot.Progress.ProgressRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Test
    void getShouldReturnEmptyList() throws Exception {
        this.mockMvc.perform(get(PROGRESS_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getProgress() throws Exception {
        ArrayList<Integer> visitedLocations = new ArrayList<>(Arrays.asList(1, 2));
        Progress progress = new Progress(1, 1, 1, 1, visitedLocations, "You are close!");

        mockMvc.perform(MockMvcRequestBuilders
                .post(PROGRESS_URL)
                .content(objectMapper.writeValueAsString(progress))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.huntId").exists());
    }

    @Test
    void getProgressIsNotNull() throws Exception {
        ArrayList<Integer> visitedLocations = new ArrayList<>(Arrays.asList(1, 2));
        Progress progress = new Progress(1,1, 1, 1, visitedLocations, "You are close!");

        given(progressRepository.findAll())
                .willReturn(List.of(progress));
        Iterable<Progress> progressList = progressController.getProgress();

        assertThat(progressList).isNotNull();
    }

}
