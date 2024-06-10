package goorm.honjaya.domain.image.exception;

public class ProfileImageNotFoundException extends RuntimeException {

    public ProfileImageNotFoundException() {
        super("프로필 이미지를 찾을 수 없습니다.");
    }

}
