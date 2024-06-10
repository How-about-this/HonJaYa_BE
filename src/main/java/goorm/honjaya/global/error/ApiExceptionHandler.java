package goorm.honjaya.global.error;

import goorm.honjaya.domain.board.exception.BoardNotFoundException;
import goorm.honjaya.domain.image.exception.ProfileImageNotFoundException;
import goorm.honjaya.domain.user.exception.FailedLogoutException;
import goorm.honjaya.domain.user.exception.UserNotFountException;
import goorm.honjaya.global.common.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.RefreshFailedException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(bindingResult));
    }

    @ExceptionHandler(RefreshFailedException.class)
    public ResponseEntity<ApiResponse<?>> handleRefreshFailedException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<?>> handleExpiredJwtException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(FailedLogoutException.class)
    public ResponseEntity<ApiResponse<?>> handleFailedLogoutException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(UserNotFountException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFountException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(ProfileImageNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleProfileImageNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleBoardNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(exception.getMessage()));
    }

}
