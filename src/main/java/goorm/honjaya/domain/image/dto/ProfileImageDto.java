package goorm.honjaya.domain.image.dto;

import goorm.honjaya.domain.image.entity.ProfileImage;
import lombok.Builder;
import lombok.Data;

@Data
public class ProfileImageDto {

    private Long id;

    private String imageUrl;

    @Builder
    private ProfileImageDto(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public static ProfileImageDto from(ProfileImage profileImage) {
        return ProfileImageDto.builder()
                .id(profileImage.getId())
                .imageUrl(profileImage.getImageUrl())
                .build();
    }
}
