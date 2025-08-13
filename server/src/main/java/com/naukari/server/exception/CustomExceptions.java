package com.naukari.server.exception;

public class CustomExceptions {
    // user exceptions
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class EmailServiceException extends RuntimeException {
        public EmailServiceException(String message) {
            super(message);
        }

        public EmailServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }

    // company exceptions
    public static class CompanyNotFoundException extends RuntimeException {
        public CompanyNotFoundException(String message) {
            super(message);
        }
    }

    public static class CompanyAlreadyExistsException extends RuntimeException {
        public CompanyAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidCompanyDataException extends RuntimeException {
        public InvalidCompanyDataException(String message) {
            super(message);
        }
    }
}