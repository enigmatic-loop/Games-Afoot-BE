package com.GamesAfoot.GamesAfoot.Progress;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProgressController {

    private final ProgressRepository progressRepository;

    public ProgressController(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @GetMapping("/progress")
    public Iterable<Progress> getProgress() {
        return this.progressRepository.findAll();
    }

    @PostMapping("/progress")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Progress updateProgress(@RequestBody Progress progress) {
        return this.progressRepository.save(progress);
    }
}
