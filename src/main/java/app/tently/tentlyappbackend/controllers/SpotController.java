package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.Spot;
import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.modelsDTO.SpotDTO;
import app.tently.tentlyappbackend.services.SpotService;
import app.tently.tentlyappbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class SpotController {

    private final SpotService spotService;
    private final UserService userService;

    public SpotController(SpotService spotService, UserService userService) {
        this.spotService = spotService;
        this.userService = userService;
    }

    @GetMapping(value = "spot/{id}")
    public ResponseEntity<Object> getSpot(@PathVariable Long id) {
        Optional<Spot> spotOptional = spotService.findSpot(id);
        if (spotOptional.isPresent())
            return new ResponseEntity<>(spotOptional, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "spot")
    public ResponseEntity<Object> getSpots() {
        List<Spot> spotList = spotService.findAllSpot();
        if (!spotList.isEmpty())
            return new ResponseEntity<>(spotList, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "spot")
    public ResponseEntity<Object> addSpot(@RequestPart("json") SpotDTO spotDTO, @RequestPart("file") MultipartFile[] files) throws IOException {
        Spot spot = spotService.mapDTOToSpot(spotDTO);
        Optional<User> optionalUser = userService.findUser(spotDTO.getUser_id());
        Optional<Spot> optionalSpot = spotService.findSpotByName(spot.getName());
        if (optionalSpot.isEmpty() && optionalUser.isPresent()) {
            spot.setUser(optionalUser.get());
            spotService.saveSpot(spot, files);
            return new ResponseEntity<>(spot, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(value = "spot/{id}")
    public ResponseEntity<Object> updateSpot(@RequestPart("json") SpotDTO spotDTO, @RequestPart("file") MultipartFile[] files, @PathVariable Long id) throws IOException {
        Spot spot = spotService.mapDTOToSpot(spotDTO);
        Optional<User> optionalUser = userService.findUser(spotDTO.getUser_id());
        Optional<Spot> optionalSpot = spotService.findSpot(id);
        if (optionalSpot.isPresent() && optionalUser.isPresent()) {
            spot.setUser(optionalUser.get());
            spot.setId(id);
            spotService.saveSpot(spot, files);
            return new ResponseEntity<>(spot, HttpStatus.OK);
        } else if (spot.getId() != null && !spot.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "spot/{id}")
    public HttpStatus deleteSpot(@PathVariable Long id) {
        Optional<Spot> spotOptional = spotService.findSpot(id);
        if (spotOptional.isPresent()) {
            spotService.deleteSpot(id);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

}