package com.fourfourfour.damoa.common.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(value = "ResponseDto : 응답 정보", description = "클라이언트로 응답할 데이터와 HTTP 응답 코드를 가집니다.")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponseDto {
    @ApiModelProperty(value = "HTTP 응답 코드")
    private int status;
    @ApiModelProperty(value = "응답 데이터")
    private Object data;
}
