package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeDto;

public interface NoticeService {

    Notice register(NoticeDto.RegisterDto registerDto, Long memberSeq);

    void deleteAll();
}
