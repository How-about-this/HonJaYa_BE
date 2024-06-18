package goorm.honjaya.domain.user.service;

import goorm.honjaya.domain.user.exception.FailedLogoutException;
import goorm.honjaya.global.common.RedisService;
import goorm.honjaya.global.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new FailedLogoutException("쿠키가 비어있습니다");
        }

        Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refresh_token"))
                .findFirst();

        if (refreshTokenCookie.isEmpty()) {
            throw new FailedLogoutException("refresh_token이 없습니다");
        }

        String refreshToken = refreshTokenCookie.get().getValue();
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new FailedLogoutException("refresh_token이 없습니다");
        }

        String key = jwtUtil.getUsername(refreshToken);

        if (!redisService.exists(key)) {
            throw new FailedLogoutException("redis에 refresh_token이 저장되어 있지 않습니다");
        }

        redisService.delete(key);

        String accessToken = request.getHeader("Authorization").substring(7);
        if (!jwtUtil.isExpired(accessToken)) {
            Duration expiration = Duration.ofMillis(jwtUtil.getExpiration(accessToken));
            redisService.set(accessToken, "blacklisted", expiration);
        }

        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.setStatus(HttpServletResponse.SC_OK);
        response.addCookie(cookie);
    }
}
