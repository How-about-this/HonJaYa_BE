package goorm.honjaya.domain.user.service;

import goorm.honjaya.domain.image.entity.ProfileImage;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.user.repository.UserRepository;
import goorm.honjaya.global.auth.CustomOAuth2User;
import goorm.honjaya.global.auth.KakaoResponse;
import goorm.honjaya.global.auth.OAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 부모 클래스의 메서드를 사용하여 객체를 생성함.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 제공자
        String registration = userRequest.getClientRegistration().getRegistrationId();

        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuth2Response oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        String username = oAuth2Response.getProviderId();

        // 넘어온 회원정보가 이미 DB에 존재하는지 확인
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            User user = User.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .token(accessToken)
                    .role("ROLE_USER")
                    .status("NEW")
                    .build();

            user.addProfileImage(new ProfileImage(oAuth2Response.getProfileImage()));

            userRepository.save(user);

            return new CustomOAuth2User(user);
        } else {
            User user = optionalUser.get();
            user.setToken(accessToken);
            userRepository.save(user);
            return new CustomOAuth2User(user);
        }
    }
}
