package com.fourfourfour.damoa.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponseDto<T> {

    private final T data;

    @Builder
    public BaseResponseDto(T data) {
        this.data = data;
    }
}
