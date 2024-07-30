package com.GamesAfoot.service;

import com.GamesAfoot.model.Location;
import com.GamesAfoot.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public Location updateLocation(Long id, Location location) {
        if (locationRepository.existsById(id)) {
            location.setId(id);
            return locationRepository.save(location);
        }
        return null;
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
