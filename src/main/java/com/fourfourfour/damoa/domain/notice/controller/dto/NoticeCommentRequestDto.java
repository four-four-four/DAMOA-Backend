package com.fourfourfour.damoa.domain.notice.controller.dto;

import com.fourfourfour.damoa.domain.notice.service.dto.NoticeCommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
public class NoticeCommentRequestDto {

    @Getter
    @ToString(of = {"noticeSeq", "content"})
    @NoArgsConstructor
    public static class RegisterDto {

        private Long noticeSeq;

        @NotBlank(message = "{blank.notice.comment}")
        private String content;

        @Builder
        public RegisterDto(Long noticeSeq, String content) {
            this.noticeSeq = noticeSeq;
            this.content = content;
        }

        public NoticeCommentDto.RegisterDto toServiceDto() {
            return NoticeCommentDto.RegisterDto.builder()
                    .noticeSeq(noticeSeq)
                    .content(content)
                    .build();
        }
    }

}
