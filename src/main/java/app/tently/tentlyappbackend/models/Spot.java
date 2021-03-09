package app.tently.tentlyappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "spots")
@DynamicUpdate
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @NotNull
    private String name;

    private String description;

    private String country;

    private String region;

    private int likeCount;

    private int shareCount;

    @ElementCollection
    private List<String> imgUrls;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likeList;

}
