package app.tently.tentlyappbackend.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "usedTokens")
public class TokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;
    @Column(name = "token")
    private String token;
}
