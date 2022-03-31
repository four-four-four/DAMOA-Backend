package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.member.controller.dto.MemberRequestDto;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.service.MemberService;
import com.fourfourfour.damoa.domain.notice.controller.dto.NoticeRequestDto;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.repository.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

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

    private MemberRequestDto.RegisterDto adminRegisterDto;

    private NoticeRequestDto.RegisterDto noticeRegisterDto;

    @BeforeEach
    public void setUp() {

        memberService.deleteAll();
        noticeService.deleteAll();
        /**
         * 관리자 프로젝트 구성이 완료될 때까지
         * adminRegisterDto를 관리자로 가정합니다.
         */
        adminRegisterDto = MemberRequestDto.RegisterDto.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender("female")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        noticeRegisterDto = NoticeRequestDto.RegisterDto.builder()
                .title("DAMOA 공지사항 제목")
                .content("DAMOA 공지사항 본문")
                .build();
    }

    @Test
    @DisplayName("공지사항 등록 - 성공")
    public void noticeRegisterSuccess() {
        // 회원가입
        Member savedAdmin = memberService.register(adminRegisterDto.toServiceDto());

        // 공지사항 등록
        Notice savedNotice = noticeService.register(noticeRegisterDto.toServiceDto(), savedAdmin.getSeq());
        em.flush();
        em.clear();

        // 작성된 공지사항 등록 검증
        Optional<Notice> findNotice = noticeRepository.findBySeq(savedNotice.getSeq());
        assertThat(noticeRegisterDto.getTitle()).isEqualTo(findNotice.get().getTitle());
        assertThat(noticeRegisterDto.getContent()).isEqualTo(findNotice.get().getContent());
    }

    @Test
    @DisplayName("공지사항 등록 - 예외 처리 : 관리자가 존재하지 않을 때")
    public void registerFailWhenAdminNull() {
        assertThatThrownBy(() -> noticeService.register(noticeRegisterDto.toServiceDto(), 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_MEMBER);
    }
}