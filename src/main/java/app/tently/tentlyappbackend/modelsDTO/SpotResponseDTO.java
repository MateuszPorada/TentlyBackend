package app.tently.tentlyappbackend.modelsDTO;

import app.tently.tentlyappbackend.models.Spot;
import lombok.Data;

@Data
public class SpotResponseDTO {
    private Spot spot;
    private int likeCount;

    public SpotResponseDTO(Spot spot) {
        this.spot = spot;
        this.likeCount = spot.getLikeList().size();
    }


}
