package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.notice.service.dto.NoticeResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NoticeRepositoryCustom {
    Integer countAllNotice();

    Optional<List<NoticeResponseDto.ForPage>> findNoticeForPage(Pageable pageable);
}
