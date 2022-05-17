package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.member.controller.dto.MemberRequestDto;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.service.MemberService;
import com.fourfourfour.damoa.domain.notice.controller.dto.NoticeRequestDto;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.repository.NoticeRepository;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
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

    private MemberRequestDto.RegisterDto adminRegisterDto1, memberRegisterDto1;

    private NoticeRequestDto.RegisterDto noticeRegisterDto1, noticeRegisterDto2, noticeRegisterDto3;

    @BeforeEach
    public void setUp() {

        memberService.deleteAll();
        noticeService.deleteAll();
        /**
         * 관리자 프로젝트 구성이 완료될 때까지
         * adminRegisterDto를 관리자로 가정합니다.
         */
        adminRegisterDto1 = MemberRequestDto.RegisterDto.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender("female")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        memberRegisterDto1 = MemberRequestDto.RegisterDto.builder()
                .email("test2@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender("male")
                .birthDate(LocalDate.of(1998, 10, 11))
                .job("전문직")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        String title = "DAMOA 공지사항 제목";
        String content = "DAMOA 공지사항 본문";

        noticeRegisterDto1 = NoticeRequestDto.RegisterDto.builder()
                .title(title + "1")
                .content(content + "1")
                .build();

        noticeRegisterDto2 = NoticeRequestDto.RegisterDto.builder()
                .title(title + "2")
                .content(content + "2")
                .build();

        noticeRegisterDto3 = NoticeRequestDto.RegisterDto.builder()
                .title(title + "3")
                .content(content + "3")
                .build();
    }

    @Test
    @DisplayName("공지사항 등록 - 성공")
    public void noticeRegisterSuccess() {
        // 회원가입
        Member savedAdmin = memberService.register(adminRegisterDto1.toServiceDto());

        // 공지사항 등록
        Notice savedNotice = noticeService.register(noticeRegisterDto1.toServiceDto(), savedAdmin.getSeq());
        em.flush();
        em.clear();

        // 작성된 공지사항 등록 검증
        Optional<Notice> findNotice = noticeRepository.findBySeq(savedNotice.getSeq());
        assertThat(noticeRegisterDto1.getTitle()).isEqualTo(findNotice.get().getTitle());
        assertThat(noticeRegisterDto1.getContent()).isEqualTo(findNotice.get().getContent());
    }

    @Test
    @DisplayName("공지사항 등록 - 예외 처리 : 관리자가 존재하지 않을 때")
    public void registerFailWhenAdminNull() {
        assertThatThrownBy(() -> noticeService.register(noticeRegisterDto1.toServiceDto(), 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_MEMBER);
    }

    @Test
    @DisplayName("공지사항 목록 조회 - 성공")
    public void findNoticeForPageSuccess() throws InterruptedException{
        // 공지사항 목록 존재하지 않을 때 검증
        PageRequest pageable = PageRequest.of(1, 3);
        NoticeResponseDto.NoticeListPage page1 = noticeService.getPage(pageable);
        assertThat(page1.getTotalCount()).isEqualTo(0);
        assertThat(page1.getCurrentPage()).isEqualTo(1);
        assertThat(page1.getTotalPage()).isEqualTo(0);

        // 회원가입
        Member savedAdmin = memberService.register(adminRegisterDto1.toServiceDto());
        memberService.register(memberRegisterDto1.toServiceDto());

        // 공지사항 등록
        noticeService.register(noticeRegisterDto1.toServiceDto(), savedAdmin.getSeq());
        Thread.sleep(1000);
        noticeService.register(noticeRegisterDto2.toServiceDto(), savedAdmin.getSeq());
        Thread.sleep(1000);
        noticeService.register(noticeRegisterDto3.toServiceDto(), savedAdmin.getSeq());

        // 작성된 공지사항 목록 페이징 검증
        NoticeResponseDto.NoticeListPage page2 = noticeService.getPage(pageable);
        assertThat(page2.getTotalCount()).isEqualTo(3);
        assertThat(page2.getCurrentPage()).isEqualTo(1);
        assertThat(page2.getTotalPage()).isEqualTo(1);
        assertThat(page2.getNoticeForPage().size()).isEqualTo(3);

        // 작성된 공지사항 목록 검증
        List<NoticeResponseDto.ForPage> findNoticeForPage = page2.getNoticeForPage();
        NoticeResponseDto.ForPage noticeForPage1 = findNoticeForPage.get(0);
        assertThat(noticeForPage1.getTitle()).isEqualTo(noticeRegisterDto3.getTitle());
        assertThat(noticeForPage1.getWriter()).isEqualTo(savedAdmin.getNickname());
        assertThat(noticeForPage1.getViews()).isEqualTo(0);

        NoticeResponseDto.ForPage noticeForPage2 = findNoticeForPage.get(1);
        assertThat(noticeForPage2.getTitle()).isEqualTo(noticeRegisterDto2.getTitle());
        assertThat(noticeForPage2.getWriter()).isEqualTo(savedAdmin.getNickname());
        assertThat(noticeForPage2.getViews()).isEqualTo(0);

        NoticeResponseDto.ForPage noticeForPage3 = findNoticeForPage.get(2);
        assertThat(noticeForPage3.getTitle()).isEqualTo(noticeRegisterDto1.getTitle());
        assertThat(noticeForPage3.getWriter()).isEqualTo(savedAdmin.getNickname());
        assertThat(noticeForPage3.getViews()).isEqualTo(0);
    }

    @Test
    @DisplayName("공지사항 상세페이지 조회 - 성공")
    public void findNoticeDetailSuccess() {
        // 회원가입
        Member savedAdmin = memberService.register(adminRegisterDto1.toServiceDto());
        memberService.register(memberRegisterDto1.toServiceDto());

        // 공지사항 등록
        Notice savedNotice = noticeService.register(noticeRegisterDto1.toServiceDto(), savedAdmin.getSeq());

        // 공지사항 상세페이지 조회 검증
        NoticeResponseDto.Detail findNotice = noticeService.getDetail(savedNotice.getSeq());
        assertThat(findNotice.getNoticeSeq()).isEqualTo(savedNotice.getSeq());
        assertThat(findNotice.getTitle()).isEqualTo(savedNotice.getTitle());
        assertThat(findNotice.getContent()).isEqualTo(savedNotice.getContent());
        assertThat(findNotice.getViews()).isEqualTo(1);
        assertThat(findNotice.getCreatedDate()).isEqualTo(savedNotice.getCreatedDate().toLocalDate());
        assertThat(findNotice.getWriter()).isEqualTo(savedNotice.getWriter().getNickname());
    }
}