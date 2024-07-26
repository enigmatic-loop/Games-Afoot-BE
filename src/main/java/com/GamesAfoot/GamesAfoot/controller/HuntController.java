package com.GamesAfoot.GamesAfoot.controller;

import com.GamesAfoot.GamesAfoot.model.Hunt;
import com.GamesAfoot.GamesAfoot.service.HuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hunts")
public class HuntController {

    @Autowired
    private HuntService huntService;

    @GetMapping
    public List<Hunt> getAllHunts() {
        return huntService.getAllHunts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hunt> getHuntById(@PathVariable Long id) {
        return huntService.getHuntById(id);
    }

    @PostMapping
    public Hunt createHunt(@RequestBody Hunt hunt) {
        return huntService.createHunt(hunt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hunt> updateHunt(@PathVariable Long id, @RequestBody Hunt hunt) {
        return huntService.updateHunt(id, hunt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHunt(@PathVariable Long id) {
        return huntService.deleteHunt(id);
    }
}
