package app.tently.tentlyappbackend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageResponseModel extends ImageModel {
    private String imageUrl;
}
