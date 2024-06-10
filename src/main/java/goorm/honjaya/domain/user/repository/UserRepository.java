package goorm.honjaya.domain.user.repository;

import goorm.honjaya.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = { "profile", "ideal" })
    Optional<User> findByUsername(String username);

    @Override
    @EntityGraph(attributePaths = { "profile", "ideal" })
    Optional<User> findById(Long id);
}
