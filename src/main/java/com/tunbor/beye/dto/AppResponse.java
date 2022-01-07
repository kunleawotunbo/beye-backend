package com.tunbor.beye.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */

@AllArgsConstructor
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AppResponse<T> {

    private boolean success;

    private List<AppError> errors;

    private final T body;


    public static <T> AppResponse<T> createSuccess(T model) {
        return new AppResponse<>(true, null, model);
    }


    public static <T> AppResponse<T> createError(String... errorMessages) {
        return new AppResponse<>(false,
                Arrays.stream(errorMessages).map(AppError::from).collect(Collectors.toList()),
                null);
    }


    public static <T> AppResponse<T> createError(List<String> errorMessages) {
        return new AppResponse<>(false,
                errorMessages.stream().map(AppError::from).collect(Collectors.toList()),
                null);
    }


    public static <T> AppResponse<T> createError(String errorMessage, AppErrorCode errorCode) {
        return new AppResponse<>(false,
                Collections.singletonList(new AppError(errorMessage, errorCode)),
                null);
    }


    public static <T> AppResponse<T> createError(AppError error) {
        return new AppResponse<>(false, Collections.singletonList(error), null);
    }


    public static <T> AppResponse<T> createError(Collection<AppError> errors) {
        return new AppResponse<>(false, new ArrayList<>(errors), null);
    }


    @AllArgsConstructor
    @Getter
    public static class AppError {

        private String message;

        private AppErrorCode code;

        public static AppError from(String errorMessage) {
            return new AppError(errorMessage, AppErrorCode.COMMON_ERROR);
        }
    }
}
