package goorm.honjaya.domain.user.controller;

import goorm.honjaya.domain.user.service.ReissueService;
import goorm.honjaya.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.RefreshFailedException;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping("/reissue")
    public ApiResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) throws RefreshFailedException {
        reissueService.reissue(request, response);
        return ApiResponse.success();
    }
}
