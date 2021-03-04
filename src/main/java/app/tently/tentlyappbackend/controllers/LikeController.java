package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.Like;
import app.tently.tentlyappbackend.models.Spot;
import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.services.LikeService;
import app.tently.tentlyappbackend.services.SpotService;
import app.tently.tentlyappbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LikeController {
    private final SpotService spotService;
    private final UserService userService;
    private final LikeService likeService;

    public LikeController(UserService userService, SpotService spotService, LikeService likeService) {
        this.spotService = spotService;
        this.userService = userService;
        this.likeService = likeService;
    }

    @GetMapping(value = "like")
    public ResponseEntity<Object> getLikes() {
        List<Like> likeList = likeService.findAllLikes();
        if (!likeList.isEmpty()) {
            return new ResponseEntity<>(likeList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "like/{id}")
    public ResponseEntity<Object> getLike(@PathVariable Long id) {
        Optional<Like> optionalLike = likeService.findLikeById(id);
        if (optionalLike.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalLike.get(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "like/{user_id}")
    public ResponseEntity<Object> getLikeByUser(@PathVariable Long user_id) {
        Optional<User> optionalUser = userService.findUser(user_id);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalUser.get().getLikes(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "like/{spot_id}")
    public ResponseEntity<Object> getLikeBySpot(@PathVariable Long spot_id) {
        Optional<Spot> optionalSpot = spotService.findSpotById(spot_id);
        if (optionalSpot.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<Like> likeList = likeService.findLikeSpot(optionalSpot.get());
            return new ResponseEntity<>(likeList, HttpStatus.OK);
        }
    }


    @PostMapping(value = "like/{user_id}/{spot_id}")
    public ResponseEntity<Object> addLike(@PathVariable Long user_id, @PathVariable Long spot_id) {
        Optional<User> optionalUser = userService.findUser(user_id);
        Optional<Spot> optionalSpot = spotService.findSpot(spot_id);
        if (optionalUser.isPresent() && optionalSpot.isPresent()) {
            Optional<Like> optionalLike = likeService.findLikeBySpotAndUser(optionalUser.get(), optionalSpot.get());
            if (optionalLike.isEmpty()) {
                Like like = likeService.addLike(optionalUser.get(), optionalSpot.get());
                return new ResponseEntity<>(like, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "like/{user_id}/{spot_id}")
    public ResponseEntity<Object> deleteLike(@PathVariable Long user_id, @PathVariable Long spot_id) {
        Optional<User> optionalUser = userService.findUser(user_id);
        Optional<Spot> optionalSpot = spotService.findSpot(spot_id);
        if (optionalUser.isPresent() && optionalSpot.isPresent()) {
            Optional<Like> optionalLike = likeService.findLikeBySpotAndUser(optionalUser.get(), optionalSpot.get());
            if (optionalLike.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                likeService.deleteLike(optionalLike.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
