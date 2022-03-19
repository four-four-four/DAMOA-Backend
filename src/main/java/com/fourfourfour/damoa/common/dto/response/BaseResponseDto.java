package com.fourfourfour.damoa.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponseDto {

    private final String message;

    private final Object data;

    @Builder
    public BaseResponseDto(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    @Builder
    public BaseResponseDto(String message) {
        this(message, null);
    }

    @Builder
    public BaseResponseDto(Object data) {
        this(null, data);
    }
}
