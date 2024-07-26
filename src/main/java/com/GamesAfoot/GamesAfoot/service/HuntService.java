package com.GamesAfoot.GamesAfoot.service;

import com.GamesAfoot.GamesAfoot.model.Hunt;
import com.GamesAfoot.GamesAfoot.repository.HuntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HuntService {

    @Autowired
    private HuntRepository huntRepository;

    public List<Hunt> getAllHunts() {
        return huntRepository.findAll();
    }

    public ResponseEntity<Hunt> getHuntById(Long id) {
        Optional<Hunt> hunt = huntRepository.findById(id);
        return hunt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Hunt createHunt(Hunt hunt) {
        return huntRepository.save(hunt);
    }

    public ResponseEntity<Hunt> updateHunt(Long id, Hunt hunt) {
        if (!huntRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hunt.setId(id);
        return ResponseEntity.ok(huntRepository.save(hunt));
    }

    public ResponseEntity<Void> deleteHunt(Long id) {
        if (!huntRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        huntRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
