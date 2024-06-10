package goorm.honjaya.domain.user.controller;

import goorm.honjaya.domain.user.dto.ProfileDto;
import goorm.honjaya.domain.user.service.ProfileService;
import goorm.honjaya.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/users/{id}/profile")
    public ApiResponse<?> save(@PathVariable("id") Long id, @RequestBody ProfileDto profileDto) {
        profileService.save(id, profileDto);
        return ApiResponse.success();
    }
}
