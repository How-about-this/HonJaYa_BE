package goorm.honjaya.domain.image.repository;

import goorm.honjaya.domain.image.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    List<ProfileImage> findByUser_Id(Long userId);

    Optional<ProfileImage> findByIdAndUserId(Long id, Long userId);

}
