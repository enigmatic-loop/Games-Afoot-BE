package com.GamesAfoot;

import com.GamesAfoot.Models.Progress;
import com.GamesAfoot.Controllers.ProgressController;
import com.GamesAfoot.Repositories.ProgressRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void getAllProgressReturns200() throws Exception {
        this.mockMvc.perform(get(PROGRESS_URL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void postProgressReturns201() throws Exception {
        ArrayList<Object> foundLocations = new ArrayList<>(Arrays.asList(1, true));
        foundLocations.add(2, true);
        foundLocations.add(3, false);
        Progress progress = new Progress(1, 1, 1, 1, foundLocations, "You are close!");

        mockMvc.perform(MockMvcRequestBuilders
                .post(PROGRESS_URL)
                .content(objectMapper.writeValueAsString(progress))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.huntId").exists());
    }

    @Test
    public void getAllProgressIsNotNull() throws Exception {
        ArrayList<Object> foundLocations = new ArrayList<>(Arrays.asList(1, true));
        foundLocations.add(2, true);
        foundLocations.add(3, false);
        Progress progress = new Progress(1,1, 1, 1, foundLocations, "You are close!");

        given(progressRepository.findAll())
                .willReturn(List.of(progress));
        Iterable<Progress> progressList = progressController.getAllProgress();

        assertThat(progressList).isNotNull();
    }

}
