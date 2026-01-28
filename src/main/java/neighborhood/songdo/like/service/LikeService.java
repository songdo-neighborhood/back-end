package neighborhood.songdo.like.service;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.like.domain.Like;
import neighborhood.songdo.like.repository.LikeRepository;
import neighborhood.songdo.restaurant.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final RestaurantService restaurantService;

    @Transactional
    public void createLike(Long id) {
        restaurantService.getRestaurantById(id);
        Like like = Like.createLike(id);
        likeRepository.save(like);
    }

    @Transactional
    public void deleteLike(Long id) {
        likeRepository.deleteByRestaurantId(id);
    }
}
