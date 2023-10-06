package dev.brunocesar.imovelsimplificado.advert.exceptions;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final int httpStatus;

    private final String message;

    public ApplicationException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ApplicationException(int httpStatus, String message, Throwable ex) {
        super(message, ex);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
