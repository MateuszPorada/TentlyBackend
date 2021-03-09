package app.tently.tentlyappbackend.models;

import lombok.Data;

@Data
public class SpotResponse {
    private Spot spot;
    private int likeCount;

    public SpotResponse(Spot spot) {
        this.spot = spot;
        this.likeCount = spot.getLikeList().size();
    }


}
