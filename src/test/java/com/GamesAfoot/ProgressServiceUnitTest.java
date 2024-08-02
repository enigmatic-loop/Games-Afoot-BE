package com.GamesAfoot;

import com.GamesAfoot.exceptions.ProgressNotFoundException;
import com.GamesAfoot.models.Progress;
import com.GamesAfoot.repositories.ProgressRepository;
import com.GamesAfoot.services.ProgressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProgressServiceUnitTest {

    @Mock
    ProgressRepository progressRepository;

    @InjectMocks
    ProgressService progressService;

    @Test
    public void testGetAllProgress() {
        Progress progress1 = new Progress(1, 1, 6, 3, false);
        Progress progress2 = new Progress(2, 1, 8, 5, true);
        when(progressRepository.findAll()).thenReturn(Arrays.asList(progress1, progress2));
        List<Progress> progressList = progressService.getAllProgress();
        assertEquals(2, progressList.size());
        assertEquals(1, progressList.getFirst().getId());
        assertEquals(1, progressList.getFirst().getUserId());
        assertEquals(6, progressList.getFirst().getHuntId());
        assertEquals(3, progressList.getFirst().getTargetLocationIndex());
        assertEquals(false, progressList.getFirst().getGameComplete());
        assertEquals(2, progressList.get(1).getId());
        assertEquals(1, progressList.get(1).getUserId());
        assertEquals(8, progressList.get(1).getHuntId());
        assertEquals(5, progressList.get(1).getTargetLocationIndex());
        assertEquals(true, progressList.get(1).getGameComplete());
    }

    @Test
    void testGetProgressById() {
        Progress progress = new Progress(3, 2, 3, 1, false);
        when(progressRepository.findById(3)).thenReturn(Optional.of(progress));
        Optional<Progress> progressById = progressRepository.findById(3);
        assertNotEquals(progressById, null);
    }

    @Test
    void testInvalidGetProgressById() {
        int invalidId = 9;
        when(progressRepository.findById(invalidId)).thenThrow(new ProgressNotFoundException(invalidId));
        ProgressNotFoundException exception = assertThrows(ProgressNotFoundException.class, () -> {
            progressService.getProgressById(invalidId);
        });
        assertTrue(exception.getMessage().contains("Progress not found with id: " + invalidId));
    }

    @Test
    void testCreateProgress() {
        Progress progress = new Progress(4, 3, 2, 1, false);
        progressService.createProgress(progress);
        verify(progressRepository, times(1)).save(progress);
        ArgumentCaptor<Progress> progressArgumentCaptor = ArgumentCaptor.forClass(Progress.class);
        verify(progressRepository).save(progressArgumentCaptor.capture());
        Progress progressCreated = progressArgumentCaptor.getValue();
        assertNotNull(progressCreated.getId());
        assertEquals(4, progressCreated.getId());
        assertEquals(3, progressCreated.getUserId());
        assertEquals(2, progressCreated.getHuntId());
        assertEquals(1, progressCreated.getTargetLocationIndex());
        assertEquals(false, progressCreated.getGameComplete());
    }

    @Test
    void testUpdateProgressByIdIncrementsTargetLocationIndex() {
        Progress progress = new Progress(5, 1, 1, 0, false);
        Progress updatedProgress = new Progress(5, 1, 1, 1, false);
        when(progressRepository.findById(5)).thenReturn(Optional.of(progress));
        when(progressRepository.save(progress)).thenReturn(updatedProgress);
        progressService.completeGameById(5);
        assertNotNull(progress.getId());
        assertEquals(5, updatedProgress.getId());
        assertEquals(1, updatedProgress.getUserId());
        assertEquals(1, updatedProgress.getHuntId());
        assertEquals(1, updatedProgress.getTargetLocationIndex());
        assertEquals(false, updatedProgress.getGameComplete());
    }

    @Test
    void testCompleteGameById() {
        Progress progress = new Progress(5, 1, 1, 0, false);
        Progress completedProgress = new Progress(5, 1, 1, 5, true);
        when(progressRepository.findById(5)).thenReturn(Optional.of(progress));
        when(progressRepository.save(progress)).thenReturn(completedProgress);
        progressService.completeGameById(5);
        assertNotNull(progress.getId());
        assertEquals(5, completedProgress.getId());
        assertEquals(1, completedProgress.getUserId());
        assertEquals(1, completedProgress.getHuntId());
        assertEquals(5, completedProgress.getTargetLocationIndex());
        assertEquals(true, completedProgress.getGameComplete());
    }
}
