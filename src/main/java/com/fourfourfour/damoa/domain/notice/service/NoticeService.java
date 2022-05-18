package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeDto;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeResponseDto;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

    Notice register(NoticeDto.RegisterDto registerDto, Long memberSeq);

    NoticeResponseDto.NoticeListPage getPage(Pageable pageable);

    NoticeResponseDto.Detail getDetail(Long noticeSeq);

    void deleteAll();
}
