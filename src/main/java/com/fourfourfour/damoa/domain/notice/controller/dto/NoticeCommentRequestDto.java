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
    @ToString(of = {"content"})
    @NoArgsConstructor
    public static class RegisterDto {

        @NotBlank(message = "{blank.notice.comment}")
        private String content;

        @Builder
        public RegisterDto(String content) {
            this.content = content;
        }

        public NoticeCommentDto.RegisterDto toServiceDto(Long noticeSeq) {
            return NoticeCommentDto.RegisterDto.builder()
                    .noticeSeq(noticeSeq)
                    .content(content)
                    .build();
        }
    }

}
