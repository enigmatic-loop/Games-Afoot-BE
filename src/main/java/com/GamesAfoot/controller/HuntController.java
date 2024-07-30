package com.GamesAfoot.controller;

import com.GamesAfoot.model.Hunt;
import com.GamesAfoot.model.Location;
import com.GamesAfoot.repository.HuntRepository;
import com.GamesAfoot.repository.LocationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hunts")
public class HuntController {

    private final HuntRepository huntRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public HuntController(HuntRepository huntRepository, LocationRepository locationRepository) {
        this.huntRepository = huntRepository;
        this.locationRepository = locationRepository;
    }

    private final String OPENAI_API_KEY = System.getenv("OPENAI_KEY");
    private final String OPENAI_URL = "https://api.openai.com/v1/engines/gpt-4/completions";

    @PostMapping
    public ResponseEntity<Hunt> createHunt(@RequestBody Hunt hunt) {
        Hunt newHunt = huntRepository.save(hunt);
        return ResponseEntity.status(201).body(newHunt);
    }

    @GetMapping
    public ResponseEntity<List<Hunt>> getHunts() {
        List<Hunt> hunts = huntRepository.findAll();
        return ResponseEntity.ok(hunts);
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<?> getHuntLocations(@PathVariable Long id) {
        Optional<Hunt> huntOpt = huntRepository.findById(id);
        if (huntOpt.isPresent()) {
            List<Location> locations = locationRepository.findByHuntId(huntOpt.get().getId());
            return ResponseEntity.ok(locations);
        }
        return ResponseEntity.status(404).body("No hunt found for ID " + id);
    }

    @PostMapping("/{id}/generate_locations")
    public ResponseEntity<?> addLocations(@PathVariable Long id) throws JsonProcessingException {
        Optional<Hunt> huntOpt = huntRepository.findById(id);
        if (!huntOpt.isPresent()) {
            return ResponseEntity.status(404).body("No hunt found for ID " + id);
        }

        Hunt hunt = huntOpt.get();

        if (!hunt.getLocations().isEmpty()) {
            return ResponseEntity.status(201).body("Locations already generated for Hunt ID " + hunt.getId());
        }

        String locationsData = generateLocations(hunt);

        if (locationsData == null) {
            return ResponseEntity.status(500).body("Failed to generate locations");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(locationsData);
        List<Location> locations = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            Location location = new Location();
            location.setName(node.get("name").asText());
            location.setLatitude(node.get("latitude").asText());
            location.setLongitude(node.get("longitude").asText());
            location.setDescription(node.get("description").asText());
            location.setClues(new ArrayList<>(mapper.convertValue(node.get("clues"), List.class)));
            location.setHunt(hunt);
            locations.add(location);
        }

        locationRepository.saveAll(locations);
        return ResponseEntity.status(201).body("Locations successfully added to Hunt ID " + hunt.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHunt(@PathVariable Long id, @RequestBody Hunt huntDetails) {
        Optional<Hunt> huntOpt = huntRepository.findById(id);
        if (huntOpt.isPresent()) {
            Hunt hunt = huntOpt.get();
            hunt.setStartLatitude(huntDetails.getStartLatitude());
            hunt.setStartLongitude(huntDetails.getStartLongitude());
            hunt.setDistance(huntDetails.getDistance());
            hunt.setNumSites(huntDetails.getNumSites());
            hunt.setGameType(huntDetails.getGameType());
            Hunt updatedHunt = huntRepository.save(hunt);
            return ResponseEntity.ok(updatedHunt);
        }
        return ResponseEntity.status(404).body("No hunt found for ID " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHunt(@PathVariable Long id) {
        Optional<Hunt> huntOpt = huntRepository.findById(id);
        if (huntOpt.isPresent()) {
            huntRepository.delete(huntOpt.get());
            return ResponseEntity.ok("Hunt ID " + id + " deleted successfully");
        }
        return ResponseEntity.status(404).body("No hunt found for ID " + id);
    }

    private String generateLocations(Hunt hunt) {
        String inputMessage = String.format(
                "Generate a JSON array of %s %s within exactly %s miles from the user's location, which is (%s, %s) from start to finish. DO NOT GO OUT OF BOUNDS OF THE WALKING DISTANCE. DO NOT MAKE UP FICTIONAL LOCATIONS. Each object should include a string data type for 'name', 'latitude', 'longitude', 'description', and a JSON array of 3 'clues'. Make sure to ONLY respond with a JSON ARRAY, without any characters before or after the JSON, and NEVER A STRING REPRESENTATION OF THE JSON ARRAY. Note: If you cannot find real and legitimate locations in the user's location that meet the prompt's request, then just insert the string values in the JSON prompting the user as to why you couldn't find any more real locations within the distance given, whether that's distance requirements or the kind of things they want to see in their treasure hunt.",
                hunt.getNumSites(), hunt.getGameType(), hunt.getDistance(), hunt.getStartLatitude(), hunt.getStartLongitude()
        );

        RestTemplate restTemplate = new RestTemplate();
        String requestBody = "{\"prompt\": \"" + inputMessage + "\", \"max_tokens\": 1024}";

        try {
            return restTemplate.postForObject(OPENAI_URL, createHttpEntity(requestBody), String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpEntity<String> createHttpEntity(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(body, headers);
    }
}
