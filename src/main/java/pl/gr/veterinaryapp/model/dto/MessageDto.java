package pl.gr.veterinaryapp.model.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class MessageDto {

    private final HttpStatus httpStatus;
    private final String message;
}
