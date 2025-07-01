package com.naukari.server.utils;

import com.naukari.server.model.enums.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateResponseEntity {
    public <T> ResponseEntity<?> createResponse(CustomResponse<T> response) {
        HttpStatus httpStatus = mapStatusToHttp(response.getStatus());
        assert httpStatus != null;
        return new ResponseEntity<>(response, httpStatus);
    }

    private static HttpStatus mapStatusToHttp(ResponseStatus status) {
        return switch (status) {
            case SUCCESS -> HttpStatus.OK;
            case CREATED -> HttpStatus.CREATED;
            case ACCEPTED -> HttpStatus.ACCEPTED;
            case NO_CONTENT -> HttpStatus.NO_CONTENT;

            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case METHOD_NOT_ALLOWED -> HttpStatus.METHOD_NOT_ALLOWED;
            case CONFLICT, ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case VALIDATION_FAILED -> HttpStatus.UNPROCESSABLE_ENTITY;

            case SERVICE_UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            case ERROR -> null;
        };
    }
}