package goorm.honjaya.domain.user.controller;

import goorm.honjaya.domain.user.dto.ProfileDto;
import goorm.honjaya.domain.user.service.ProfileService;
import goorm.honjaya.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/users/{id}/profile")
    public ApiResponse<?> save(@PathVariable("id") Long id, @RequestBody ProfileDto profileDto) {
        profileService.save(id, profileDto);
        return ApiResponse.success();
    }

    @GetMapping("/users/{id}/profile")
    public ApiResponse<?> findByUserId(@PathVariable("id") Long id) {
        ProfileDto profileDto = profileService.findByUserId(id);
        return ApiResponse.success(profileDto);
    }

    @PutMapping("/users/{id}/profile")
    public ApiResponse<?> update(@PathVariable Long id,
                                 @RequestBody ProfileDto profileDto) {
        profileService.update(id, profileDto);
        return ApiResponse.success();
    }
}
