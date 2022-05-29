package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.notice.service.dto.NoticeCommentDto;

import java.util.List;
import java.util.Optional;

public interface NoticeCommentRepositoryCustom {
    Optional<List<NoticeCommentDto.Detail>> findCommentsByNoticeSeq(Long noticeSeq);
}
