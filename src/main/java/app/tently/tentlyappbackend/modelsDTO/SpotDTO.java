package app.tently.tentlyappbackend.modelsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SpotDTO {
    private String name;
    private String description;
    private String country;
    private String region;
    private List<String> imgUrl;
    private Long user_id;


    public SpotDTO(String name, String description, String country, String region, List<String> imgUrl) {
        this.name = name;
        this.description = description;
        this.country = country;
        this.region = region;
        this.imgUrl = imgUrl;
    }
}
