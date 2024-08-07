package com.GamesAfoot.unit;

import com.GamesAfoot.models.Progress;
import com.GamesAfoot.controllers.ProgressController;

import com.GamesAfoot.services.ProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProgressController.class)
public class ProgressControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgressService progressService;

    private final static String PROGRESS_URL = "/progress";
    private Progress progress;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        progress = new Progress(1, 1, 1, 3, false);
    }

    @Test
    public void testGetAllProgress() throws Exception {
        when(progressService.getAllProgress()).thenReturn(Collections.singletonList(progress));
        mockMvc.perform(get(PROGRESS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].userId").exists())
                .andExpect(jsonPath("$[0].huntId").exists())
                .andExpect(jsonPath("$[0].targetLocationIndex").exists())
                .andExpect(jsonPath("$[0].gameComplete").exists());
    }

    @Test
    public void testGetProgressById() throws Exception {
        when(progressService.getProgressById(1)).thenReturn(progress);
        mockMvc.perform(get(PROGRESS_URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.huntId", is(1)))
                .andExpect(jsonPath("$.targetLocationIndex", is(3)))
                .andExpect(jsonPath("$.gameComplete", is(false)))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testCreateProgress() throws Exception {
        when(progressService.createProgress(progress)).thenReturn(progress);

        mockMvc.perform(
            post(PROGRESS_URL)
                    .content(objectMapper.writeValueAsString(new Progress(null, 1 ,2, 3, false)))
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateProgressById() throws Exception {
        when(progressService.updateProgressById(progress.getId())).thenReturn(new Progress(null, 1 ,2, 4, false));
        mockMvc.perform(patch(PROGRESS_URL + "/" + 1 + "/update-progress"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.targetLocationIndex", is(4)));
    }

    @Test
    public void testCompleteGameById() throws Exception {
        when(progressService.completeGameById(progress.getId())).thenReturn(new Progress(1, 1 ,2, 3, true));
        mockMvc.perform(patch(PROGRESS_URL + "/" + 1 + "/complete-game"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameComplete", is(true)));
    }

    @Test
    public void testDeleteProgress() throws Exception {
        when(progressService.deleteProgressById(progress.getId())).thenReturn(true);
        mockMvc.perform(delete(PROGRESS_URL + "/" + progress.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
