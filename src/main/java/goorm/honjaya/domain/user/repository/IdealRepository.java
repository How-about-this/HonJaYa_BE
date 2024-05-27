package goorm.honjaya.domain.user.repository;

import goorm.honjaya.domain.user.entity.Ideal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdealRepository extends JpaRepository<Ideal, Long> {
}
