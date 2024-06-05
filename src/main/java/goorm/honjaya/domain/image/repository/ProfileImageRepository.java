package goorm.honjaya.domain.image.repository;

import goorm.honjaya.domain.image.entity.BoardImage;
import goorm.honjaya.domain.image.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    List<ProfileImage> findByUser_Id(Long userId);

}
