package com.fourfourfour.damoa.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponseDto<T> {

    private final String message;

    private final T data;

    @Builder
    public BaseResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
