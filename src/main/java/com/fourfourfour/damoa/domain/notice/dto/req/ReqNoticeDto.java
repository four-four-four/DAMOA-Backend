package com.fourfourfour.damoa.domain.notice.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ReqNoticeDto {

    @NotBlank(message = "{blank.notice.title}")
    private String title;

    @NotBlank(message = "{blank.notice.content}")
    private String content;

    @Builder
    public ReqNoticeDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
