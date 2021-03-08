package app.tently.tentlyappbackend.modelsDTO;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
