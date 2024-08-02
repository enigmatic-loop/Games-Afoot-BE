package com.GamesAfoot.Controllers;

import com.GamesAfoot.Exceptions.InvalidInputException;
import com.GamesAfoot.Exceptions.ProgressNotFoundException;
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

    // GET routes
    @GetMapping("/progress")
    public Iterable<Progress> getAllProgress() {
        return this.progressRepository.findAll();
    }

    @GetMapping("/progress/{id}")
    public Object getProgressBId(@PathVariable Integer id) {
        return progressRepository.findById(id).orElseThrow(() -> new ProgressNotFoundException(id));
    }

    // POST routes
    @PostMapping("/progress")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Progress postProgress(@RequestBody Progress progress) {
        try {
            return progressRepository.save(progress);
        } catch (Exception InvalidInput) {
            throw new InvalidInputException(progress);
        }
    }

    // PATCH routes
    @PatchMapping("/progress/{id}")
    public Progress updateProgress(@PathVariable Integer id) {
        return progressRepository.findById(id)
                .map(progress -> {
                    progress.setTargetLocationIndex(progress.getTargetLocationIndex() + 1);
                    return progressRepository.save(progress);
                })
                .orElseThrow(() -> new ProgressNotFoundException(id));
    }

    // DELETE routes
    @DeleteMapping("/progress/{id}")
    public String deleteProgress(@PathVariable Integer id) {
        try {
            progressRepository.findById(id).orElseThrow(() -> new ProgressNotFoundException(id));
            progressRepository.deleteById(id);
            return String.format("Progress with id: %d successfully deleted.", id);
        } catch (Exception ProgressNotFound) {
            throw new ProgressNotFoundException(id);
        }
    }
}
