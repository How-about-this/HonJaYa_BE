package goorm.honjaya.domain.image.entity;

import goorm.honjaya.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Entity
@DiscriminatorValue("P")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isPrimary = Boolean.FALSE;

    private boolean isKakaoImage = Boolean.FALSE;

    @Builder
    private ProfileImage(String originalImageName, String saveImageName, String extension,String imageUrl, User user, boolean isPrimary, boolean isKakaoImage) {
        super(originalImageName, saveImageName, extension, imageUrl);
        this.user = user;
        this.isPrimary = isPrimary;
        this.isKakaoImage = isKakaoImage;
    }

}
