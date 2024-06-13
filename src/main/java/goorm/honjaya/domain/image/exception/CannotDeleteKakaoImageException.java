package goorm.honjaya.domain.image.exception;

public class CannotDeleteKakaoImageException extends RuntimeException {
    public CannotDeleteKakaoImageException() {
        super("카카오 프로필 이미지는 삭제할 수 없습니다.");
    }
}
