package pl.gr.veterinaryapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FailedOperationException extends RuntimeException {

    private final HttpStatus httpStatus;

    public FailedOperationException() {
        super();
        httpStatus = null;
    }

    public FailedOperationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
