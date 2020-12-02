package app.tently.tentlyappbackend.modelsDTO;

import lombok.Data;

@Data
public class SpotDTO {
    private String name;
    private String imgUrl;
    private String description;
    private Long user_id;
}
