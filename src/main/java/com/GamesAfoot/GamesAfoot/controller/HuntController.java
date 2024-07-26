package com.GamesAfoot.GamesAfoot.controller;

import com.GamesAfoot.GamesAfoot.model.Hunt;
import com.GamesAfoot.GamesAfoot.model.Location;
import com.GamesAfoot.GamesAfoot.repository.HuntRepository;
import com.GamesAfoot.GamesAfoot.repository.LocationRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hunts")
public class HuntController {

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ChatClient chatClient;

    @GetMapping
    public List<Hunt> getAllHunts() {
        return huntRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hunt> getHuntById(@PathVariable Long id) {
        Optional<Hunt> hunt = huntRepository.findById(id);
        return hunt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hunt createHunt(@RequestBody Hunt hunt) {
        return huntRepository.save(hunt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hunt> updateHunt(@PathVariable Long id, @RequestBody Hunt huntDetails) {
        Optional<Hunt> optionalHunt = huntRepository.findById(id);
        if (optionalHunt.isPresent()) {
            Hunt hunt = optionalHunt.get();
            hunt.setStartLatitude(huntDetails.getStartLatitude());
            hunt.setStartLongitude(huntDetails.getStartLongitude());
            hunt.setDistance(huntDetails.getDistance());
            hunt.setNumSites(huntDetails.getNumSites());
            hunt.setGameType(huntDetails.getGameType());
            final Hunt updatedHunt = huntRepository.save(hunt);
            return ResponseEntity.ok(updatedHunt);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHunt(@PathVariable Long id) {
        Optional<Hunt> hunt = huntRepository.findById(id);
        if (hunt.isPresent()) {
            huntRepository.delete(hunt.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/generate_locations")
    public ResponseEntity<String> generateLocations(@PathVariable Long id) {
        Optional<Hunt> optionalHunt = huntRepository.findById(id);
        if (optionalHunt.isPresent()) {
            Hunt hunt = optionalHunt.get();
            String locationsData = generateLocations(hunt);

            if (locationsData == null) {
                return ResponseEntity.status(500).body("Failed to generate locations");
            }

            // Process the generated locations and save them
            List<Location> locations = processLocations(locationsData, hunt);
            locationRepository.saveAll(locations);

            return ResponseEntity.ok("Locations successfully added to Hunt ID " + hunt.getId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String generateLocations(Hunt hunt) {
        String inputMessage = String.format("Generate a JSON array of %s %s within exactly %s miles from the user's location, which is (%s, %s) from start to finish. DO NOT GO OUT OF BOUNDS OF THE WALKING DISTANCE. DO NOT MAKE UP FICTIONAL LOCATIONS. Each object should include a string data type for 'name', 'start_latitude', 'start_longitude', 'description', and a JSON array of 3 'clues'. Make sure to ONLY respond with a JSON ARRAY, without any characters before or after the JSON, and NEVER A STRING REPRESENTATION OF THE JSON ARRAY. Note: If you cannot find real and legitimate locations in the user's location that meet the prompt's request, then just insert the string values in the JSON prompting the user as to why you couldn't find any more real locations within the distance given, whether that's distance requirements or the kind of things they want to see in their treasure hunt.",
                hunt.getNumSites(), hunt.getGameType(), hunt.getDistance(), hunt.getStartLatitude(), hunt.getStartLongitude());

        String response = chatClient.prompt()
                .user(inputMessage)
                .call()
                .content();

        return response;
    }

    private List<Location> processLocations(String locationsData, Hunt hunt) {
        // Process the locationsData to create Location objects and return them
        // You can use a JSON parser to parse the locationsData and create Location objects
        // For simplicity, assume locationsData is a valid JSON array string
        // Implement this method to parse and create Location objects
        return List.of(); // Placeholder return statement
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<List<Location>> getHuntLocations(@PathVariable Long id) {
        List<Location> locations = locationRepository.findByHuntId(id);
        if (locations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(locations);
    }
}
