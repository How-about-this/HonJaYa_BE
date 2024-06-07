package goorm.honjaya.domain.test;

import goorm.honjaya.global.common.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;

import java.io.IOException;

@RestController
@Slf4j
public class RedirectController {

    @GetMapping("/redirect")
    public void redirect(HttpServletResponse response) throws IOException {
        log.info("리다이렉트 호출");
        response.sendRedirect("https://k2b3bc621690aa.user-app.krampoline.com/redirect");
    }
}
