package app.tently.tentlyappbackend.modelsDTO;

import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String password;
    private String nickname;
    private String country;
    private String region;

    public UserDTO(String email, String password, String nickname, String country, String region) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.country = country;
        this.region = region;
    }

    public UserDTO() {

    }
}
