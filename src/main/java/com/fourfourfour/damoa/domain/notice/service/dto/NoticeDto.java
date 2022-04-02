package com.fourfourfour.damoa.domain.notice.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class NoticeDto {

    @Getter
    @ToString(of = {"title", "content"})
    public static class RegisterDto {
        private String title;

        private String content;

        @Builder
        public RegisterDto(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
