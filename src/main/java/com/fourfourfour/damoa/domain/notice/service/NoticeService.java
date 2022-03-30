package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.domain.notice.dto.req.ReqNoticeDto;
import com.fourfourfour.damoa.domain.notice.entity.Notice;

public interface NoticeService {

    Notice register(ReqNoticeDto reqNoticeDto, Long memberSeq);

    void deleteAll();
}
