package goorm.honjaya.domain.user.repository;

import goorm.honjaya.domain.user.entity.Ideal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdealRepository extends JpaRepository<Ideal, Long> {
    Optional<Ideal> findByUserId(Long id);
}
