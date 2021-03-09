package app.tently.tentlyappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String nickname;

    private String country = null;

    private String region = null;

    private String confirmationToken;

    private String imageUrl = "default";

    @JsonIgnore
    private String role = "USER";

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Spot> spots;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

}
