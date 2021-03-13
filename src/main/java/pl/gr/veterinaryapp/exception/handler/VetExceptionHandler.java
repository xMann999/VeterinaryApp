package pl.gr.veterinaryapp.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.gr.veterinaryapp.exception.FailedOperationException;

@ControllerAdvice
public class VetExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {FailedOperationException.class})
    protected ResponseEntity<Object> operationFailed(
            FailedOperationException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), ex.getHttpStatus(), request);
    }
}
