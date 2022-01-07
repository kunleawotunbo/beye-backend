package com.tunbor.beye.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */

@Getter
public enum AppErrorCode {

    COMMON_ERROR("0001"),

    UNAUTHORIZED_ERROR("0010");

    private String code;

    AppErrorCode(String code) {
        this.code = code;
    }

    @Override
    @JsonValue
    public String toString() {
        return code;
    }
}
