package app.tently.tentlyappbackend.modelsDTO;

import app.tently.tentlyappbackend.models.User;
import lombok.Data;

@Data
public class SpotDTO {
    private String name;
    private String imgUrl;
    private String description;
    private User user;
}
