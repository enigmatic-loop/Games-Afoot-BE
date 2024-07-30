//package com.GamesAfoot.controller;
//
//import com.GamesAfoot.model.Location;
//import com.GamesAfoot.repository.LocationRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/locations")
//public class LocationController {
//
//    private final LocationRepository locationRepository;
//
//    public LocationController(LocationRepository locationRepository) {
//        this.locationRepository = locationRepository;
//    }
//
//    @PostMapping
//    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
//        Location savedLocation = locationRepository.save(location);
//        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getLocation(@PathVariable Long id) {
//        Optional<Location> optionalLocation = locationRepository.findById(id);
//        if (optionalLocation.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Location not found for ID " + id);
//        }
//        return ResponseEntity.ok(optionalLocation.get());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location updatedLocation) {
//        Optional<Location> optionalLocation = locationRepository.findById(id);
//        if (optionalLocation.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(null);
//        }
//
//        Location location = optionalLocation.get();
//        location.setName(updatedLocation.getName());
//        location.setLatitude(updatedLocation.getLatitude());
//        location.setLongitude(updatedLocation.getLongitude());
//        location.setDescription(updatedLocation.getDescription());
//        location.setClues(updatedLocation.getClues());
//
//        Location savedLocation = locationRepository.save(location);
//        return ResponseEntity.ok(savedLocation);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
//        if (!locationRepository.existsById(id)) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Location not found for ID " + id);
//        }
//
//        locationRepository.deleteById(id);
//        return ResponseEntity.ok("Location ID " + id + " deleted successfully");
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Location>> getAllLocations() {
//        List<Location> locations = locationRepository.findAll();
//        return ResponseEntity.ok(locations);
//    }
//}
