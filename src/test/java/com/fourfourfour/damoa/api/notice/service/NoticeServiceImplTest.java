package com.fourfourfour.damoa.api.notice.service;

import com.fourfourfour.damoa.api.member.controller.dto.MemberRequestDto;
import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.service.MemberService;
import com.fourfourfour.damoa.api.notice.dto.req.ReqNoticeDto;
import com.fourfourfour.damoa.api.notice.entity.Notice;
import com.fourfourfour.damoa.api.notice.repository.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MemberRequestDto.RegisterDto registerDto1;

    @BeforeEach
    public void setUp() {

        memberService.deleteAll();
        noticeService.deleteAll();

        registerDto1 = MemberRequestDto.RegisterDto.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender("female")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();
    }

    @Test
    @DisplayName("공지사항 등록 테스트")
    public void register() {

        Member savedMember = memberService.register(registerDto1.toServiceDto());

        ReqNoticeDto reqNoticeDto = ReqNoticeDto.builder()
                .title("DAMOA 공지사항 제목")
                .content("DAMOA 공지사항 본문")
                .build();

        Notice savedNotice = noticeService.register(reqNoticeDto, savedMember.getSeq());
        em.flush();
        em.clear();

        Notice findNotice = noticeRepository.findBySeq(savedNotice.getSeq());
        assertThat(reqNoticeDto.getTitle()).isEqualTo(findNotice.getTitle());
        assertThat(reqNoticeDto.getContent()).isEqualTo(findNotice.getContent());
    }
}