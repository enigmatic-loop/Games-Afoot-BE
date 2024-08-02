package com.GamesAfoot.controllers;

import com.GamesAfoot.exceptions.InvalidInputException;
import com.GamesAfoot.exceptions.ProgressNotFoundException;
import com.GamesAfoot.models.Progress;
import com.GamesAfoot.repositories.ProgressRepository;
import com.GamesAfoot.services.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProgressController {

    @Autowired
    private ProgressService progressService;

//    public ProgressController(ProgressRepository progressRepository) {
//        this.progressRepository = progressRepository;
//    }

    // GET routes
    @GetMapping("/progress")
    public ResponseEntity<List<Progress>> getAllProgress() {
        return ResponseEntity.ok().body(progressService.getAllProgress());
    }

    @GetMapping("/progress/{id}")
    public ResponseEntity<Progress> getProgressById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(progressService.getProgressById(id));
    }

    // POST routes
    @PostMapping("/progress")
//    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Progress> createProgress(@RequestBody Progress progress) {
        Progress newProgress = progressService.createProgress(progress);
        return new ResponseEntity<>(newProgress, HttpStatus.CREATED);
    }

    // PATCH routes
    @PatchMapping("/progress/{id}/update-progress")
    public ResponseEntity<Progress> updateProgress(@PathVariable Integer id) {
        return ResponseEntity.ok().body(progressService.updateProgressById(id));
    }

    @PatchMapping("/progress/{id}/complete-game")
    public ResponseEntity<Progress> completeGameById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(progressService.completeGameById(id));
    }

    // DELETE routes
    @DeleteMapping("/progress/{id}")
    public ResponseEntity<String> deleteProgressById(@PathVariable Integer id) {
        boolean deleteProgressById = progressService.deleteProgressById(id);
        if (deleteProgressById) {
            return new ResponseEntity<>((String.format("Progress with id: %d successfully deleted.", id)), HttpStatus.OK);
        } else {
            throw new ProgressNotFoundException(id);
        }
    }
}
