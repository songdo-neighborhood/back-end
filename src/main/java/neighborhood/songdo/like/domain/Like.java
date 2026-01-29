package neighborhood.songdo.like.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    public static Like createLike(Long restaurantId) {
        return Like.builder()
                .restaurantId(restaurantId)
                .build();
    }
}
