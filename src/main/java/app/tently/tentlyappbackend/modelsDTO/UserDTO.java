package app.tently.tentlyappbackend.modelsDTO;

import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String password;
    private String nickname;
    private String country;
    private String region;
}
