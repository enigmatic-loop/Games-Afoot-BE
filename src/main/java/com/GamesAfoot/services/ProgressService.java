package com.GamesAfoot.services;

import com.GamesAfoot.exceptions.ProgressNotFoundException;
import com.GamesAfoot.models.Progress;
import com.GamesAfoot.repositories.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }

    public Progress getProgressById(Integer id) {
        return progressRepository.findById(id).orElseThrow(() -> new ProgressNotFoundException(id));
    }

    public Progress createProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    public Progress updateProgressById(Integer id) {
        return progressRepository.findById(id)
                .map(progress -> {
                    progress.setTargetLocationIndex(progress.getTargetLocationIndex() + 1);
                    return progressRepository.save(progress);
                })
                .orElseThrow(() -> new ProgressNotFoundException(id));
    }

    public Boolean deleteProgressById(Integer id) {
        Optional<Progress> progress = progressRepository.findById(id);
        if (progress.isPresent()) {
            progressRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
