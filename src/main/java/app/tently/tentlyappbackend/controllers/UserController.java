package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.modelsDTO.UserDTO;
import app.tently.tentlyappbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findUser(id);
        if (optionalUser.isPresent())
            return new ResponseEntity<>(optionalUser, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "user")
    public ResponseEntity<Object> getUserList() {
        List<User> userList = userService.findAllUsers();
        if (!userList.isEmpty())
            return new ResponseEntity<>(userList, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "user")
    public ResponseEntity<Object> addUser(@RequestBody UserDTO userDTO) {
        User user = userService.mapDTOToUser(userDTO);
        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            String userPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(userPassword);
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(value = "user/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        User user = userService.mapDTOToUser(userDTO);
        Optional<User> optionalUser = userService.findUser(id);
        if (optionalUser.isPresent()) {
            user.setId(id);
            String userPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(userPassword);
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else if (user.getId() != null && !user.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "user/{id}")
    public HttpStatus deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findUser(id);
        if (optionalUser.isPresent()) {
            userService.deleteUser(id);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }
}
