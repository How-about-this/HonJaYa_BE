package goorm.honjaya.domain.user.exception;

public class IdealNotFoundException extends RuntimeException {
    public IdealNotFoundException() {
        super("취향 정보를 찾을 수 없습니다.");
    }
}
