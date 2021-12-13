package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.modelsDTO.UserDTO;
import app.tently.tentlyappbackend.modelsDTO.UserLoginResponseDTO;
import app.tently.tentlyappbackend.repos.LikeRepo;
import app.tently.tentlyappbackend.repos.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final LikeRepo likeRepo;
    private final ModelMapper modelMapper;
    @Value("${SIGNATURE.KEY}")
    private String sigKey;

    public UserService(UserRepo userRepo, LikeRepo likeRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.likeRepo = likeRepo;
        this.modelMapper = modelMapper;
    }

    public Optional<User> findUser(Long id) {
        return userRepo.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User mapDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<User> findUserByToken(String token) {
        return userRepo.findByConfirmationToken(token);
    }

    public void saveUser(User user) {
        if (user.getImageUrl().equals("default")) {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/image/")
                    .path("2a78f32a-bdd8-4a2e-a3fb-bdf3c5debd55")
                    .toUriString();
            user.setImageUrl(imageUrl);
        }
        userRepo.save(user);
    }

    public UserLoginResponseDTO mapTOUserResponse(User user) {
        System.out.println(sigKey);
        long currentTime = System.currentTimeMillis();
        List<Object> likes = likeRepo.findLikeByUserID(user.getId());
        return new UserLoginResponseDTO(user, Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("name", user.getEmail())
                .claim("role", "ROLE_" + user.getRole())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + 2000000))
                .signWith(SignatureAlgorithm.HS256, sigKey.getBytes())
                .compact(), likes);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
