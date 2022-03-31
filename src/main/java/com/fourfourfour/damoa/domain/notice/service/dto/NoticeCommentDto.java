package com.fourfourfour.damoa.domain.notice.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class NoticeCommentDto {

    @Getter
    @ToString(of = {"noticeSeq", "content"})
    public static class RegisterDto {
        private Long noticeSeq;

        private String content;

        @Builder
        public RegisterDto(Long noticeSeq, String content) {
            this.noticeSeq = noticeSeq;
            this.content = content;
        }
    }
}
