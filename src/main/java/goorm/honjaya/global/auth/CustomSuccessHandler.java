package goorm.honjaya.global.auth;

import goorm.honjaya.global.common.RedisService;
import goorm.honjaya.global.util.CookieUtil;
import goorm.honjaya.global.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customUserDetail = (CustomOAuth2User) authentication.getPrincipal();

        // 토큰 생성시에 사용자명과 권한이 필요
        String username = customUserDetail.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // accessToken과 refreshToken 생성
        // accessToken 만료시간 : 1시간
        String accessToken = jwtUtil.createJwt("access", username, role, Duration.ofHours(1).toMillis());
        // refreshToken 만료시간 : 2주
        String refreshToken = jwtUtil.createJwt("refresh", username, role, Duration.ofDays(14).toMillis());

        // redis에 insert (key = username, value = refreshToken)
        redisService.set(username, refreshToken, Duration.ofDays(14L));

        // refreshToken은 쿠키를 통하여 응답
        response.addCookie(cookieUtil.createCookie("refresh_token", refreshToken, (int) Duration.ofHours(14).toSeconds()));
        response.setStatus(HttpServletResponse.SC_OK);
        // 프론트엔드에서 리다이렉트를 받으면 헤더값은 바로 빼낼 수 없기 때문에, URL 파라미터로 access token을 전달
        response.sendRedirect("https://k2b3bc621690aa.user-app.krampoline.com/landing/authcallback?access_token=" + accessToken);

    }


}
