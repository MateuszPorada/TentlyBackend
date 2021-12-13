package app.tently.tentlyappbackend.modelsDTO;

import app.tently.tentlyappbackend.models.User;
import lombok.Data;

import java.util.List;


@Data
public class UserLoginResponseDTO {
    private final User user;
    private String token;
    private List<Object> likedSpotsIdList;

    public UserLoginResponseDTO(User user, String token, List<Object> likedSpotsIdList) {
        this.user = user;
        this.token = token;
        this.likedSpotsIdList = likedSpotsIdList;
    }


}
