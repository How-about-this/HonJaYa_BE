package goorm.honjaya.domain.user.service;

import goorm.honjaya.global.common.RedisService;
import goorm.honjaya.global.util.CookieUtil;
import goorm.honjaya.global.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.security.auth.RefreshFailedException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisService redisService;

    public void reissue(HttpServletRequest request, HttpServletResponse response) throws RefreshFailedException {

        // 쿠키에 존재하는 refresh_token을 가져온다
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    refreshToken = cookie.getValue();
                }
            }
        } else {
            throw new RefreshFailedException("refresh_token의 값이 NULL입니다");
        }

        // 유효기간 확인
        if (jwtUtil.isExpired(refreshToken)) {
            throw new RefreshFailedException("refresh_token이 만료되었습니다");
        }
        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {
            throw new RefreshFailedException("refresh_token을 전달받지 못했습니다");
        }

        // 새로운 Token을 만들기 위해 준비
        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // Redis내에 존재하는 refreshToken인지 확인
        if (!redisService.exists(username)) {
            throw new RefreshFailedException("Redis에 저장된 refresh_token이 아닙니다");
        }

        // 새로운 JWT 생성
        String newAccessToken = jwtUtil.createJwt("access", username, role, Duration.ofHours(1).toMillis());
        String newRefreshToken = jwtUtil.createJwt("refresh", username, role, Duration.ofDays(14).toMillis());

        // redis에 refreshToken 업데이트
        redisService.delete(username);
        redisService.set(username, newRefreshToken, Duration.ofDays(14));

        // 응답
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
        response.addCookie(cookieUtil.createCookie("refresh_token", newRefreshToken, (int) Duration.ofDays(14).toMinutes()));
    }
}
