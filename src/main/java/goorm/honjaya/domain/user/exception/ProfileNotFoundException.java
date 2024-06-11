package goorm.honjaya.domain.user.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException() {
        super("요청하신 회원 정보를 찾을 수 없습니다.");
    }
}
