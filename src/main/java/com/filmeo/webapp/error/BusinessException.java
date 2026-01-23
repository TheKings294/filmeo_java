package com.filmeo.webapp.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorType type;

    public BusinessException(ErrorType type, Object... args) {
        super(String.format(type.getTemplate(), args));
        this.type = type;
    }
}
