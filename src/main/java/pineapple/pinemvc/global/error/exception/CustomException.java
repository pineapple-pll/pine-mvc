package pineapple.pinemvc.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import pineapple.pinemvc.global.error.ErrorCode;

@Getter
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, HttpStatus status) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.status = status;
    }

}

