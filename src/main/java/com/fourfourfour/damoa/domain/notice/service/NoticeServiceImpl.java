package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.repository.NoticeRepository;
import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeDto;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Notice register(NoticeDto.RegisterDto registerDto, Long memberSeq) {

        Member findMember = memberRepository.findBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        Notice newNotice = Notice.builder()
                .title(registerDto.getTitle())
                .content(registerDto.getContent())
                .writer(findMember)
                .build();

        return noticeRepository.save(newNotice);
    }

    @Override
    public NoticeResponseDto.NoticeListPage getPage(Pageable pageable) {
        Integer totalCount = noticeRepository.countAllNotice();
        Integer totalPage = (int) Math.ceil((double) totalCount / pageable.getPageSize());

        List<NoticeResponseDto.ForPage> noticeForPage = noticeRepository.findNoticeForPage(pageable)
                .orElse(null);

        if (noticeForPage == null) {
            noticeForPage = new ArrayList<>();
        }

        return NoticeResponseDto.NoticeListPage.builder()
                .totalCount(totalCount)
                .totalPage(totalPage)
                .currentPage(pageable.getPageNumber())
                .noticeForPage(noticeForPage)
                .build();
    }

    @Transactional
    @Override
    public void deleteAll() {
        noticeRepository.deleteAll();
    }
}
