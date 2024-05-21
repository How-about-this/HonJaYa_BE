package goorm.honjaya.global.auth;

public interface OAuth2Response {

    String getProvider();

    String getProviderId();

    String getName();

    String getProfileImage();
}
