package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.domain.notice.entity.NoticeComment;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeCommentDto;

public interface NoticeCommentService {

    NoticeComment register(NoticeCommentDto.RegisterDto registerDto, Long memberSeq);

    void deleteAll();
}
