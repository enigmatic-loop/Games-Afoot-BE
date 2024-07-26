package com.GamesAfoot.GamesAfoot.service;

import com.GamesAfoot.GamesAfoot.model.Location;
import com.GamesAfoot.GamesAfoot.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public ResponseEntity<Location> getLocationById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public ResponseEntity<Location> updateLocation(Long id, Location location) {
        if (!locationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        location.setId(id);
        return ResponseEntity.ok(locationRepository.save(location));
    }

    public ResponseEntity<Void> deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        locationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
