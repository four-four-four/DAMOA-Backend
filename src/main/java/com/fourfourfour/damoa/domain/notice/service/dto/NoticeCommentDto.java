package com.fourfourfour.damoa.domain.notice.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Getter
    @ToString(of = {"commentSeq", "content", "createdDate", "memberSeq", "writer"})
    public static class Detail {
        private Long commentSeq;

        private String content;

        private String createdDate;

        private Long memberSeq;

        private String writer;

        @Builder
        @QueryProjection
        public Detail(Long commentSeq, String content, LocalDateTime createdDate, Long memberSeq, String writer) {
            this.commentSeq = commentSeq;
            this.content = content;
            this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.memberSeq = memberSeq;
            this.writer = writer;
        }
    }
}
