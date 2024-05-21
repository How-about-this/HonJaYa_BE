package goorm.honjaya.domain.user.controller;

import goorm.honjaya.domain.user.dto.UserDto;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.exception.UserNotFountException;
import goorm.honjaya.domain.user.service.UserService;
import goorm.honjaya.global.auth.CustomOAuth2User;
import goorm.honjaya.global.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/current")
    public ApiResponse<UserDto> findCurrentUser(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) throws UserNotFountException {
        String username = customOAuth2User.getUsername();
        User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFountException("로그인된 유저를 찾을 수 없습니다."));
        return ApiResponse.success(UserDto.of(user));
    }
}
