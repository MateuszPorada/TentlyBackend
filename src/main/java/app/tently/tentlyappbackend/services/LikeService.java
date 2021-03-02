package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.Like;
import app.tently.tentlyappbackend.models.Spot;
import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.repos.LikeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepo likeRepo;

    public LikeService(LikeRepo likeRepo) {
        this.likeRepo = likeRepo;
    }

    public Optional<Like> findLikeBySpotAndUser(User user, Spot spot) {
        return likeRepo.findBySpotAndUser(spot, user);
    }

    public Like addLike(User user, Spot spot) {
        Like like = new Like();
        like.setUser(user);
        like.setSpot(spot);
        likeRepo.save(like);
        return like;
    }

    public Optional<Like> findLikeById(Long id) {
        return likeRepo.findById(id);
    }

    public List<Like> findAllLikes() {
        return likeRepo.findAll();
    }

    public List<Like> findLikeSpot(Spot spot) {
        return likeRepo.findAllBySpot(spot);
    }

    public void deleteLike(Like like) {
        likeRepo.delete(like);
    }
}
