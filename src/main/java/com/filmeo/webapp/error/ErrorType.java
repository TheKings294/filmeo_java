package com.filmeo.webapp.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "%s not found"),
    ENTITY_ALREADY_EXISTS(HttpStatus.CONFLICT, "%s already exists"),
    FORBIDDEN_ACTION(HttpStatus.FORBIDDEN, "Action forbidden");

    private final HttpStatus status;
    private final String template;

    ErrorType(HttpStatus status, String template) {
        this.status = status;
        this.template = template;
    }
}
