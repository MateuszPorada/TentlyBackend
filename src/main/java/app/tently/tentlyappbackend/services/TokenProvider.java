package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.TokenModel;
import app.tently.tentlyappbackend.repos.TokenRepo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class TokenProvider {

    private final TokenRepo tokenRepo;

    public boolean validateToken(String token) {
        Optional<TokenModel> tokenModel = tokenRepo.findByToken(token);
        return tokenModel.isEmpty();
    }
}