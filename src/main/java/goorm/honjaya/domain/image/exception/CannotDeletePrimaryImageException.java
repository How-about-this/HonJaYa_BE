package goorm.honjaya.domain.image.exception;

public class CannotDeletePrimaryImageException extends RuntimeException {
    public CannotDeletePrimaryImageException() {
        super("대표 이미지는 삭제할 수 없습니다.");
    }
}
