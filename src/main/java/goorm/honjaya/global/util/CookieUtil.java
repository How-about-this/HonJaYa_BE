package goorm.honjaya.global.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    // 쿠키 생성 메서드
    public Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
//        cookie.setSecure();
        cookie.setHttpOnly(true);
        return cookie;
    }
}
