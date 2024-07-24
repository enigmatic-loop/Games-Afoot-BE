package com.GamesAfoot.GamesAfoot.Progress;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProgressController {

    private final ProgressRepository progressRepository;

    public ProgressController(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @GetMapping("/progress")
    public Iterable<ProgressModel> getProgress() {
        return this.progressRepository.findAll();
    }

    @PostMapping("/progress")
    public ProgressModel updateProgress(@RequestBody ProgressModel progress) {
        return this.progressRepository.save(progress);
    }
}
