package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.notice.dto.req.ReqNoticeDto;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.repository.NoticeRepository;
import com.fourfourfour.damoa.common.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Notice register(ReqNoticeDto reqNoticeDto, Long memberSeq) {

        Member findMember = memberRepository.findBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        Notice newNotice = Notice.builder()
                .title(reqNoticeDto.getTitle())
                .content(reqNoticeDto.getContent())
                .writer(findMember)
                .build();

        return noticeRepository.save(newNotice);
    }

    @Transactional
    @Override
    public void deleteAll() {
        noticeRepository.deleteAll();
    }
}