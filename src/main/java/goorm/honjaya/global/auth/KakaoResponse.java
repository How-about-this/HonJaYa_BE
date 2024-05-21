package goorm.honjaya.global.auth;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> properties;

    private final Map<String, Object> account;

    private final String proviedrId;

    public KakaoResponse(Map<String, Object> attributes) {
        this.properties = (Map<String, Object>) attributes.get("properties");
        this.account = (Map<String, Object>) attributes.get("kakao_account");
        this.proviedrId = attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return this.proviedrId;
    }

    @Override
    public String getName() {
        return this.properties.get("nickname").toString();
    }

    @Override
    public String getProfileImage() {
        return this.properties.get("profile_image").toString();
    }
}
