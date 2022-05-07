package com.fourfourfour.damoa.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponseDto<T> {

    private final T data;

    @Builder
    public ErrorResponseDto(T data) {
        this.data = data;
    }
}
