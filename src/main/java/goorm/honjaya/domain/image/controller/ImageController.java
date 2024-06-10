package goorm.honjaya.domain.image.controller;

import goorm.honjaya.domain.image.dto.ProfileImagesDeleteDto;
import goorm.honjaya.domain.image.dto.ProfileImageDto;
import goorm.honjaya.domain.image.service.ImageService;
import goorm.honjaya.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/users/{userId}/profile-images")
    public ApiResponse<?> findProfileImages(@PathVariable Long userId) {

        List<ProfileImageDto> profileImagesDto = imageService.findProfileImages(userId);
        return ApiResponse.success(profileImagesDto);
    }

    @PostMapping("/users/{userId}/profile-images")
    public ApiResponse<?> saveProfileImages(@PathVariable Long userId,
                                            @RequestPart List<MultipartFile> multipartFiles) {

        List<ProfileImageDto> profileImagesDto = imageService.saveProfileImages(userId, multipartFiles);
        return ApiResponse.success(profileImagesDto);
    }

    @DeleteMapping("/users/{userId}/profile-images/{profileImageId}")
    public ApiResponse<?> deleteProfileImage(@PathVariable Long userId,
                                             @PathVariable Long profileImageId) {

        imageService.deleteProfileImage(userId, profileImageId);
        return ApiResponse.success();
    }

    @DeleteMapping("/users/{userId}/profile-images")
    public ApiResponse<?> deleteProfileImages(@PathVariable Long userId,
                                              @RequestBody ProfileImagesDeleteDto deleteDto) {
        imageService.deleteProfileImages(userId, deleteDto.getProfileImagesIds());
        return ApiResponse.success();
    }

    @PostMapping("/users/{userId}/profile-images/{profileImageId}")
    public ApiResponse<?> setPrimaryProfileImage(@PathVariable Long userId,
                                                 @PathVariable Long profileImageId) {

        imageService.setPrimaryProfileImage(userId, profileImageId);
        return ApiResponse.success();
    }
}
