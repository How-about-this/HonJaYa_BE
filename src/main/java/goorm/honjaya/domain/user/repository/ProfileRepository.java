package goorm.honjaya.domain.user.repository;

import goorm.honjaya.domain.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
