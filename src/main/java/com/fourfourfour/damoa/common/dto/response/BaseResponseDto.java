package com.fourfourfour.damoa.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponseDto {

    private Integer status;

    private Object data;

    @Builder
    public BaseResponseDto(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }
}
