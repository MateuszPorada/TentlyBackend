package app.tently.tentlyappbackend.models;

import lombok.Data;

import java.util.List;


@Data
public class UserLoginResponse {
    private final User user;
    private String token;
    private List<Object> likedSpotsIdList;

    public UserLoginResponse(User user, String token, List<Object> likedSpotsIdList) {
        this.user = user;
        this.token = token;
        this.likedSpotsIdList = likedSpotsIdList;
    }


}
