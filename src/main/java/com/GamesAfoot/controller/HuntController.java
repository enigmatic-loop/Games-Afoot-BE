package com.GamesAfoot.controller;

import com.GamesAfoot.model.Hunt;
import com.GamesAfoot.service.HuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hunts")
public class HuntController {

    @Autowired
    private HuntService huntService;

    @PostMapping
    public ResponseEntity<Hunt> createHunt(@RequestBody Hunt hunt) {
        return ResponseEntity.ok(huntService.createHunt(hunt));
    }

    @GetMapping
    public ResponseEntity<List<Hunt>> getAllHunts() {
        return ResponseEntity.ok(huntService.getAllHunts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hunt> getHuntById(@PathVariable Long id) {
        return huntService.getHuntById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHunt(@PathVariable Long id) {
        huntService.deleteHunt(id);
        return ResponseEntity.noContent().build();
    }
}
