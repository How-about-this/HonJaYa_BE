package goorm.honjaya.domain.image.dto;

import goorm.honjaya.domain.image.entity.ProfileImage;
import lombok.Builder;
import lombok.Data;

@Data
public class ProfileImageDto {

    private Long id;
    private String imageUrl;
    private boolean isPrimary;
    private boolean isKakaoImage;

    @Builder
    private ProfileImageDto(Long id, String imageUrl, boolean isPrimary, boolean isKakaoImage) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.isPrimary = isPrimary;
        this.isKakaoImage = isKakaoImage;
    }

    public static ProfileImageDto from(ProfileImage profileImage) {
        return ProfileImageDto.builder()
                .id(profileImage.getId())
                .imageUrl(profileImage.getImageUrl())
                .isPrimary(profileImage.isPrimary())
                .isKakaoImage(profileImage.isKakaoImage())
                .build();
    }
}
