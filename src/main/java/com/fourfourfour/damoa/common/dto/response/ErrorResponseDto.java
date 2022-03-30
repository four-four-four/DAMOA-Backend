package com.fourfourfour.damoa.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponseDto<T> {

    private final String message;

    private final T data;

    @Builder
    public ErrorResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
