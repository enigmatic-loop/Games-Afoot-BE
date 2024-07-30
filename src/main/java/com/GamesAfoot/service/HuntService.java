package com.GamesAfoot.service;

import com.GamesAfoot.model.Hunt;
import com.GamesAfoot.repository.HuntRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HuntService {

    private final HuntRepository huntRepository;

    public HuntService(HuntRepository huntRepository) {
        this.huntRepository = huntRepository;
    }

    public Hunt createHunt(Hunt hunt) {
        return huntRepository.save(hunt);
    }

    public List<Hunt> getAllHunts() {
        return huntRepository.findAll();
    }

    public Optional<Hunt> getHuntById(Long id) {
        return huntRepository.findById(id);
    }

    public Hunt updateHunt(Long id, Hunt hunt) {
        if (huntRepository.existsById(id)) {
            hunt.setId(id);
            return huntRepository.save(hunt);
        }
        return null;
    }

    public void deleteHunt(Long id) {
        huntRepository.deleteById(id);
    }
}
