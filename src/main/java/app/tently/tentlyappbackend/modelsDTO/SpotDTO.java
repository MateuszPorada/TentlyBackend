package app.tently.tentlyappbackend.modelsDTO;

import lombok.Data;

import java.util.List;

@Data
public class SpotDTO {
    private String name;
    private String description;
    private String country;
    private String region;
    private List<String> imgUrl;
    private Long user_id;
}
