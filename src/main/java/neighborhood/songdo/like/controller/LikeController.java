package neighborhood.songdo.like.controller;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.like.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/like")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{id}")
    public ResponseEntity<String> createLike(
            @PathVariable Long id
    ) {
        likeService.createLike(id);
        return new ResponseEntity<>("좋아요가 생성되었습니다.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(
            @PathVariable Long id) {
        likeService.deleteLike(id);
        return new ResponseEntity<>("좋아요가 삭제되었습니다.", HttpStatus.OK);
    }
}
