package com.GamesAfoot.Controllers;

import com.GamesAfoot.Models.Progress;
import com.GamesAfoot.Repositories.ProgressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProgressController {

    private final ProgressRepository progressRepository;

    public ProgressController(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @GetMapping("/progress")
    public Iterable<Progress> getAllProgress() {
        return this.progressRepository.findAll();
    }

    @PostMapping("/progress")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Progress updateProgress(@RequestBody Progress progress) {
        return this.progressRepository.save(progress);
    }
}
