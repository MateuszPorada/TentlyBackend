package app.tently.tentlyappbackend.repos;

import app.tently.tentlyappbackend.models.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<TokenModel, Long> {
    Optional<TokenModel> findByToken(String token);
}
