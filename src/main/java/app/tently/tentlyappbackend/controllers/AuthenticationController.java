package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.*;
import app.tently.tentlyappbackend.repos.TokenRepo;
import app.tently.tentlyappbackend.repos.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@RestController
public class AuthenticationController {

    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationController(UserRepo userRepo, TokenRepo tokenRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        System.out.println(Arrays.toString(System.getenv("SIGNATURE_KEY").getBytes()));
        System.out.println(userLoginDTO);
        Optional<User> newUser = userRepo.findByEmail(userLoginDTO.getEmail());
        if (newUser.isPresent()) {
            if (bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), newUser.get().getPassword())) {
                long currentTime = System.currentTimeMillis();

                return ResponseEntity.status(HttpStatus.OK).body(new UserLoginResponse(newUser.get(), Jwts.builder()
                        .setHeaderParam("typ", "JWT")
                        .claim("name", newUser.get().getEmail())
                        .claim("role", "ROLE_" + newUser.get().getRole())
                        .setIssuedAt(new Date(currentTime))
                        .setExpiration(new Date(currentTime + 2000000))
                        .signWith(SignatureAlgorithm.HS256, System.getenv("SIGNATURE_KEY").getBytes())
                        .compact()));
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

