package app.tently.tentlyappbackend.modelsDTO;

import app.tently.tentlyappbackend.models.ImageModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageResponseModelDTO extends ImageModel {
    private String imageUrl;
}
