package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.domain.notice.entity.NoticeComment;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeCommentDto;

import java.util.List;

public interface NoticeCommentService {

    NoticeComment register(NoticeCommentDto.RegisterDto registerDto, Long memberSeq);

    List<NoticeCommentDto.Detail> getComments(Long noticeSeq);

    void deleteAll();
}
