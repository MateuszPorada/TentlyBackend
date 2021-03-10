package app.tently.tentlyappbackend.repos;

import app.tently.tentlyappbackend.models.Spot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotRepo extends JpaRepository<Spot, Long> {
    Optional<Spot> findByName(String name);

    List<Spot> getAllByCountryAndRegionOrderByLikeListAsc(String country, String region, Pageable top);
}
