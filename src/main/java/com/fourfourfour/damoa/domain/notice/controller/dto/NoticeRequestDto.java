package com.fourfourfour.damoa.domain.notice.controller.dto;

import com.fourfourfour.damoa.domain.notice.service.dto.NoticeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
public class NoticeRequestDto {

    @Getter
    @ToString(of = {"title", "content"})
    @NoArgsConstructor
    public static class RegisterDto {

        @NotBlank(message = "{blank.notice.title}")
        private String title;

        @NotBlank(message = "{blank.notice.content}")
        private String content;

        @Builder
        public RegisterDto(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public NoticeDto.RegisterDto toServiceDto() {
            return NoticeDto.RegisterDto.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }
}
