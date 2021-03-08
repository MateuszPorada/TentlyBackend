package app.tently.tentlyappbackend.repos;

import app.tently.tentlyappbackend.models.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<ImageModel, String> {
}
