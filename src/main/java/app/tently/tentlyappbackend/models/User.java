package app.tently.tentlyappbackend.models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "email", unique = true)
    private String email;

    private String password;

    private String role;
}
