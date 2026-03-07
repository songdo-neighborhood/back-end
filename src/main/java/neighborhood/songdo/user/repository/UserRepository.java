package neighborhood.songdo.user.repository;

import neighborhood.songdo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
