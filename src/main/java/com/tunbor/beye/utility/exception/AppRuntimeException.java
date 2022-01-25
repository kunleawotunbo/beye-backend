package com.tunbor.beye.utility.exception;

import com.tunbor.beye.dto.AppErrorCode;
import com.tunbor.beye.dto.AppResponse.AppError;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
@Getter
public class AppRuntimeException extends RuntimeException {

    private List<AppError> errors;

    public AppRuntimeException() {
        super();
        initializeError(getMessage());
    }

    public AppRuntimeException(String message) {
        super(message);
        initializeError(message);
    }

    public AppRuntimeException(String message, Throwable cause) {
        super(message, cause);
        initializeError(message);
    }

    public AppRuntimeException(String message, AppErrorCode errorCode) {
        super(message);
        initializeError(message, errorCode);
    }

    public AppRuntimeException(List<AppError> errors) {
        super();
        this.errors = errors;
    }

    private void initializeError(String message) {
        initializeError(message, AppErrorCode.COMMON_ERROR);
    }

    private void initializeError(String message, AppErrorCode errorCode) {
        this.errors = Collections.singletonList(new AppError(message, errorCode));
    }
}
