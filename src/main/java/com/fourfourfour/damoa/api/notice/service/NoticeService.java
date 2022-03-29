package com.fourfourfour.damoa.api.notice.service;

import com.fourfourfour.damoa.api.notice.dto.req.ReqNoticeDto;
import com.fourfourfour.damoa.api.notice.entity.Notice;

public interface NoticeService {

    Notice register(ReqNoticeDto reqNoticeDto, Long memberSeq);

    void deleteAll();
}
