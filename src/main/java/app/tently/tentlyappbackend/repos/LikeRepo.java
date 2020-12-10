package app.tently.tentlyappbackend.repos;

import app.tently.tentlyappbackend.models.Like;
import app.tently.tentlyappbackend.models.Spot;
import app.tently.tentlyappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {
    Optional<Like> findBySpotAndUser(Spot spot, User user);

    List<Like> findAllBySpot(Spot spot);
}
