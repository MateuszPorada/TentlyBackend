package app.tently.tentlyappbackend.models;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
