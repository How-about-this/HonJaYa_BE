package goorm.honjaya.global.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final RestTemplate restTemplate;

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        // `DefaultAuthorizationCodeTokenResponseClient`의 기본 구현을 참고하여 작성
        // 토큰 요청 엔드포인트에 대한 요청을 수행
        // `requestEntityConverter`와 `restOperations.exchange`를 이용하여 구현

        log.info("proxy 적용");
        // 예시 구현
        RequestEntity<?> request = new OAuth2AuthorizationCodeGrantRequestEntityConverter().convert(authorizationGrantRequest);
        ResponseEntity<OAuth2AccessTokenResponse> response;
        try {
            response = this.restTemplate.exchange(request, OAuth2AccessTokenResponse.class);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error("invalid_token_response", "An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: " + ex.getMessage(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        }

        return response.getBody();
    }
}
