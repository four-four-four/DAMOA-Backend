package com.fourfourfour.damoa.domain.notice.service.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
public class NoticeResponseDto {

    @Getter
    @ToString(of = {"seq", "title", "views", "writer"})
    public static class ForPage {
        private final Long seq;

        private final String title;

        private final Integer views;

        private final String writer;

        @Builder
        @QueryProjection
        public ForPage(Long seq, String title, Integer views, String writer) {
            this.seq = seq;
            this.title = title;
            this.views = views;
            this.writer = writer;
        }
    }

    @Getter
    @ToString(of = {"totalCount", "currentPage", "totalPage", "noticeForPage"})
    public static class NoticeListPage {
        private final Integer totalCount;

        private final Integer currentPage;

        private final Integer totalPage;

        private final List<ForPage> noticeForPage;

        @Builder
        public NoticeListPage(Integer totalCount, Integer currentPage, Integer totalPage, List<ForPage> noticeForPage) {
            this.totalCount = totalCount;
            this.currentPage = currentPage;
            this.totalPage = totalPage;
            this.noticeForPage = noticeForPage;
        }
    }

    @Getter
    @ToString(of = {"noticeSeq", "title", "content", "views", "createdDate", "writer"})
    public static class Detail {
        private final Long noticeSeq;

        private final String title;

        private final String content;

        private final Integer views;

        private final LocalDate createdDate;

        private final String writer;

        @Builder
        public Detail(Long noticeSeq, String title, String content, Integer views, LocalDate createdDate, String writer) {
            this.noticeSeq = noticeSeq;
            this.title = title;
            this.content = content;
            this.views = views;
            this.createdDate = createdDate;
            this.writer = writer;
        }
    }
}
