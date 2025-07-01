package com.naukari.server.utils;

import com.naukari.server.model.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private ResponseStatus status;
    private String message;
    private T data;
}