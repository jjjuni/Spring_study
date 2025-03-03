package umc.spring.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.user.data.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
