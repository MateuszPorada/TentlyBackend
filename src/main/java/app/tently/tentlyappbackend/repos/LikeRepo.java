package app.tently.tentlyappbackend.repos;

import app.tently.tentlyappbackend.models.Like;
import app.tently.tentlyappbackend.models.Spot;
import app.tently.tentlyappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {
    Optional<Like> findBySpotAndUser(Spot spot, User user);

    List<Like> findAllBySpot(Spot spot);

    @Query(value = "SELECT DISTINCT spots.spot_id from spots inner join likes on spots.spot_id = likes.spot_id inner join users on users.user_id = likes.user_id where likes.user_id=:userID", nativeQuery = true)
    List<Object> findLikeByUserID(Long userID);
}
