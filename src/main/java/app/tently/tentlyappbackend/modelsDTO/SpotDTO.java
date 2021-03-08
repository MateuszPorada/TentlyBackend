package app.tently.tentlyappbackend.modelsDTO;

import lombok.Data;

import java.util.List;

@Data
public class SpotDTO {
    private String name;
    private List<String> imgUrl;
    private String description;
    private Long user_id;
}
