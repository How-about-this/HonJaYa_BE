package goorm.honjaya.domain.user.dto;

import goorm.honjaya.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {

    private String username;

    private String name;

    private String token;

    private String role;

    private String status;

    private String profileImage;

    @Builder
    private UserDto(String username, String name, String token, String role, String status, String profileImage) {
        this.username = username;
        this.name = name;
        this.token = token;
        this.role = role;
        this.status = status;
        this.profileImage = profileImage;
    }

    public static UserDto of(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .token(user.getToken())
                .role(user.getRole())
                .status(user.getStatus())
                .profileImage(user.getProfileImages().get(0).getImageUrl())
                .build();
    }
}
