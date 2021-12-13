package app.tently.tentlyappbackend.modelsDTO;

import lombok.Data;

@Data
public class ErrorMessageDTO {
    private String message;

    public ErrorMessageDTO(String message) {
        this.message = message;
    }
}
