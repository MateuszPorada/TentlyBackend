package app.tently.tentlyappbackend.models;

import lombok.Data;

@Data
public class UserLoginResponse {
    private User user;
    private String token;

    public UserLoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
