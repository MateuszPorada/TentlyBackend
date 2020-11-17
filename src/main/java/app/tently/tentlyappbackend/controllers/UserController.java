package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.repos.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping(value = "user")
    public List<User> getUser() {
        return userRepo.findAll();
    }

    @PostMapping(value = "user")
    public HttpStatus addUser(@RequestBody User user) {
        try {
            userRepo.save(user);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.NOT_FOUND;
        }
    }

    @PutMapping(value = "user/{id}")
    public HttpStatus updateUser(@RequestBody User user, @PathVariable Long id) {
        Optional<User> repoUser = userRepo.findById(id);
        if (repoUser.isPresent()) {
            user.setId(id);
            userRepo.save(user);
            return HttpStatus.OK;
        } else if (user.getId() != null && !user.getId().equals(id)) {
            return HttpStatus.CONFLICT;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    @DeleteMapping(value = "user/{id}")
    public HttpStatus deleteUser(@PathVariable Long id) {
        Optional<User> repoUser = userRepo.findById(id);
        if (repoUser.isPresent()) {
            userRepo.deleteById(id);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }


}
