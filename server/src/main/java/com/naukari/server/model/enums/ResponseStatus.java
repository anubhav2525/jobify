package com.naukari.server.model.enums;

public enum ResponseStatus {
    SUCCESS,             // Operation successful (200)
    CREATED,             // Resource created (201)
    ACCEPTED,            // Request accepted for processing (202)
    NO_CONTENT,          // Success but no content (204)

    BAD_REQUEST,         // Client sent invalid request (400)
    UNAUTHORIZED,        // Authentication required or failed (401)
    FORBIDDEN,           // Authenticated but no permission (403)
    NOT_FOUND,           // Resource not found (404)
    METHOD_NOT_ALLOWED,  // HTTP method not supported (405)
    CONFLICT,            // Conflict like duplicate record (409)

    VALIDATION_FAILED,   // Data validation failed (422)
    ALREADY_EXISTS,      // Resource already exists (e.g., duplicate email)

    INTERNAL_ERROR,      // Server-side exception (500)
    SERVICE_UNAVAILABLE, // Service temporarily unavailable (503)

    ERROR                // Generic error
}
