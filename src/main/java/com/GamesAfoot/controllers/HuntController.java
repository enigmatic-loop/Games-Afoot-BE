package com.GamesAfoot.controllers;

import com.GamesAfoot.models.Hunt;
import com.GamesAfoot.models.Location;
import com.GamesAfoot.repositories.HuntRepository;
import com.GamesAfoot.repositories.LocationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hunts")
public class HuntController {

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private LocationRepository locationRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<Hunt> createHunt(@RequestBody Hunt hunt) {
        System.out.println("Received request body: " + hunt);

        // Print fields individually for debugging
        System.out.println("Start Latitude: " + hunt.getStartLatitude());
        System.out.println("Start Longitude: " + hunt.getStartLongitude());
        System.out.println("Distance: " + hunt.getDistance());
        System.out.println("Num Sites: " + hunt.getNumSites());
        System.out.println("Game Type: " + hunt.getGameType());

        Hunt savedHunt = huntRepository.save(hunt);
        System.out.println("Saved hunt: " + savedHunt);

        return ResponseEntity.status(201).body(savedHunt);
    }

    @GetMapping
    public ResponseEntity<List<Hunt>> getHunts() {
        List<Hunt> hunts = huntRepository.findAll();
        System.out.println("Retrieved hunts: " + hunts);

        return ResponseEntity.ok(hunts);
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<?> getHuntLocations(@PathVariable Long id) {
        System.out.println("Requested hunt ID: " + id);

        Hunt hunt = huntRepository.findById(id).orElse(null);

        if (hunt == null) {
            System.out.println("Hunt with ID " + id + " not found.");
            return ResponseEntity.notFound().build();
        }

        List<Location> locations = locationRepository.findByHuntId(id);
        System.out.println("Retrieved locations: " + locations);

        return ResponseEntity.ok(locations);
    }

    //Get ALL LOCATIONS OF ALL HUNTS
    @GetMapping("/allLocations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return ResponseEntity.ok(locations);
    }

    @PostMapping("/{id}/generate_locations")
    public ResponseEntity<String> generateLocations(@PathVariable Long id) {
        System.out.println("Requested to generate locations for hunt ID: " + id);

        Hunt hunt = huntRepository.findById(id).orElse(null);

        if (hunt == null) {
            System.out.println("Hunt with ID " + id + " not found.");
            return ResponseEntity.notFound().build();
        }

        if (!hunt.getLocations().isEmpty()) {
            return ResponseEntity.status(201).body("Locations already generated for Hunt ID " + id);
        }

        ChatResponse locationsData = generateLocationsFromAI(hunt);
        if (locationsData == null) {
            return ResponseEntity.status(500).body("Failed to generate locations");
        }

        System.out.println("Generated locations data: " + locationsData);

        // Updated code starts here
        String responseContent = locationsData.getResult().getOutput().getContent().trim();  // Adjust if `.toString()` is appropriate
        List<Location> newLocations;
        try {
            JsonNode jsonNode = objectMapper.readTree(responseContent);
            newLocations = new ArrayList<>();
            for (JsonNode node : jsonNode) {
                Location location = new Location();
                location.setName(node.get("name").asText());
                location.setAddress(node.get("address").asText());
                location.setLatitude(node.get("latitude").asText());
                location.setLongitude(node.get("longitude").asText());
                location.setDescription(node.get("description").asText());
                List<String> clues = new ArrayList<>();
                node.get("clues").forEach(clueNode -> clues.add(clueNode.asText()));
                location.setClues(clues);
                location.setHunt(hunt);
                newLocations.add(location);
            }
            hunt.setLocations(newLocations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to parse generated locations");
        }
        // Updated code ends here
//        locationRepository.saveAll(newLocations);
        System.out.println("LINE 129");
        huntRepository.save(hunt);
        System.out.println("Saved locations for hunt ID " + id);

        return ResponseEntity.status(201).body("Locations successfully added to Hunt ID " + id);
    }

    private ChatResponse generateLocationsFromAI(Hunt hunt) {
        var openAiApi = new OpenAiApi(System.getenv("OPENAI_KEY"));
        var openAiChatOptions = OpenAiChatOptions.builder()
                .withModel("gpt-4-turbo") // Updated model for better results
//        Lower temperature for more deterministic responses
                .withTemperature((float) 0.2F)
// Allow repetitions
                .withFrequencyPenalty((float) 0)
//           Use all possible tokens
                .withTopP((float) 1.0F)
                .build();
        var chatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);

        ChatResponse response = chatModel.call(
                new Prompt(String.format(
                        "Generate a JSON array of %s %s within exactly %s linear miles from the user's location, which is (%s, %s) from start to finish in linear order. DO NOT GO OUT OF BOUNDS OF THE WALKING DISTANCE. 1 means all locations have to be within one mile walking distance, 2 means the user wants to walk no more than 2 miles to play the game, and so on. Ensure that all locations are real and legitimate, and that their geo-coordinates (latitude and longitude) are accurate, verified against reliable sources, and properly formatted as floating-point numbers. Each object should include a string data type for 'name', 'address, a floating-point number for 'latitude', a floating-point number for 'longitude', 'description', and a JSON array of 3 'clues'. Make sure to ONLY respond with a JSON ARRAY, without any characters before or after the JSON, and NEVER A STRING REPRESENTATION OF THE JSON ARRAY. If you cannot find real and legitimate locations in the user's location that meet the prompt's request, provide a suitable message explaining the issue, without excluding any particular community or culture.",
                        hunt.getNumSites(), hunt.getGameType(), hunt.getDistance(), hunt.getStartLatitude(), hunt.getStartLongitude())));



//        System.out.println("AI response: " + response);
        System.out.println("response.getResult().getOutput().getContent():  " + response.getResult().getOutput().getContent());
        return response;
    }


}