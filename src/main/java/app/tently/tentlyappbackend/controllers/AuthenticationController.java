package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.ErrorMessage;
import app.tently.tentlyappbackend.models.TokenModel;
import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.models.UserLoginResponse;
import app.tently.tentlyappbackend.modelsDTO.UserLoginDTO;
import app.tently.tentlyappbackend.repos.TokenRepo;
import app.tently.tentlyappbackend.repos.UserRepo;
import app.tently.tentlyappbackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthenticationController {

    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthenticationController(UserRepo userRepo, TokenRepo tokenRepo, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        Optional<User> newUser = userRepo.findByEmail(userLoginDTO.getEmail());
        if (newUser.isPresent()) {
            if (bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), newUser.get().getPassword())) {
                UserLoginResponse userLoginResponse = userService.mapTOUserResponse(newUser.get());
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(userLoginResponse);
            }
            {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorMessage("Wrong password")
                        );
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User nor found")
                    );
        }
    }

    @PostMapping(value = "/logoff")
    public HttpStatus logout(@RequestBody TokenModel tokenModel) {
        try {
            tokenRepo.save(tokenModel);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}

