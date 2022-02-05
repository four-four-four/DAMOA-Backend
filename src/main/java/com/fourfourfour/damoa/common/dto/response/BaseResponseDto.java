package com.fourfourfour.damoa.common.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDto {

    private int status;

    private Object data;

}
