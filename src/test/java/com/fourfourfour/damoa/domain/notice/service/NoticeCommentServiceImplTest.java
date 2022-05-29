package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.member.controller.dto.MemberRequestDto;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.service.MemberService;
import com.fourfourfour.damoa.domain.notice.controller.dto.NoticeCommentRequestDto;
import com.fourfourfour.damoa.domain.notice.controller.dto.NoticeRequestDto;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.entity.NoticeComment;
import com.fourfourfour.damoa.domain.notice.repository.NoticeCommentRepository;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeCommentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class NoticeCommentServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeCommentService noticeCommentService;

    @Autowired
    private NoticeCommentRepository noticeCommentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MemberRequestDto.RegisterDto adminRegisterDto1, memberRegisterDto1;

    private NoticeRequestDto.RegisterDto noticeRegisterDto1;

    @BeforeEach
    public void setUp() {

        memberService.deleteAll();
        noticeService.deleteAll();
        noticeCommentRepository.deleteAll();

        /**
         * 관리자 프로젝트 구성이 완료될 때까지
         * adminRegisterDto == 관리자
         * memberRegisterDto == 유저
         * 위와 같이 가정합니다.
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
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("전문직")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        noticeRegisterDto1 = NoticeRequestDto.RegisterDto.builder()
                .title("공지사항 제목")
                .content("공지사항 본문")
                .build();
    }

    @Test
    @DisplayName("공지사항 댓글 작성 - 성공")
    public void commentRegisterSuccess() {
        // 회원가입
        Member savedAdmin = memberService.register(adminRegisterDto1.toServiceDto());
        Member savedMember = memberService.register(memberRegisterDto1.toServiceDto());

        // 공지사항 등록
        Notice savedNotice = noticeService.register(noticeRegisterDto1.toServiceDto(), savedAdmin.getSeq());

        // 작성된 공지사항 댓글 작성
        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto = NoticeCommentRequestDto.RegisterDto.builder()
                .content("DAMOA 공지사항 댓글")
                .build();

        NoticeComment savedNoticeComment = noticeCommentService.register(noticeCommentRegisterDto.toServiceDto(savedNotice.getSeq()), savedMember.getSeq());
        em.flush();
        em.clear();

        // 작성된 댓글 검증
        Optional<NoticeComment> findNoticeComment = noticeCommentRepository.findBySeq(savedNoticeComment.getSeq());
        assertThat(noticeCommentRegisterDto.getContent()).isEqualTo(findNoticeComment.get().getContent());
    }

    @Test
    @DisplayName("공지사항 댓글 작성 - 예외 처리 : 공지사항 데이터 없을 때")
    public void commentRegisterFailWhenNoticeNull() {

        Member savedMember = memberService.register(memberRegisterDto1.toServiceDto());

        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto = NoticeCommentRequestDto.RegisterDto.builder()
                .content("DAMOA 공지사항 댓글")
                .build();

        assertThatThrownBy(() -> noticeCommentService.register(noticeCommentRegisterDto.toServiceDto(0L), savedMember.getSeq()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_NOTICE);
    }

    @Test
    @DisplayName("공지사항 댓글 조회 - 성공")
    public void viewNoticeCommentSuccess() throws InterruptedException {
        Member savedAdmin = memberService.register(adminRegisterDto1.toServiceDto());
        Member savedMember = memberService.register(memberRegisterDto1.toServiceDto());

        Notice savedNotice = noticeService.register(noticeRegisterDto1.toServiceDto(), savedAdmin.getSeq());

        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto1 = NoticeCommentRequestDto.RegisterDto.builder()
                .content("공지사항 댓글 1")
                .build();

        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto2 = NoticeCommentRequestDto.RegisterDto.builder()
                .content("공지사항 댓글 2")
                .build();

        NoticeComment savedComment1 = noticeCommentService.register(noticeCommentRegisterDto1.toServiceDto(savedNotice.getSeq()), savedMember.getSeq());
        Thread.sleep(1000);
        NoticeComment savedComment2 = noticeCommentService.register(noticeCommentRegisterDto2.toServiceDto(savedNotice.getSeq()), savedMember.getSeq());

        List<NoticeCommentDto.Detail> noticeComments = noticeCommentService.getComments(savedNotice.getSeq());
        assertThat(noticeComments.size()).isEqualTo(2);

        NoticeCommentDto.Detail comment1 = noticeComments.get(0);
        assertThat(comment1.getCommentSeq()).isEqualTo(savedComment1.getSeq());
        assertThat(comment1.getWriter()).isEqualTo(savedComment1.getWriter().getNickname());
        assertThat(comment1.getMemberSeq()).isEqualTo(savedComment1.getWriter().getSeq());
        assertThat(comment1.getContent()).isEqualTo(savedComment1.getContent());
        assertThat(comment1.getCreatedDate()).isEqualTo(savedComment1.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        NoticeCommentDto.Detail comment2 = noticeComments.get(1);
        assertThat(comment2.getCommentSeq()).isEqualTo(savedComment2.getSeq());
        assertThat(comment2.getWriter()).isEqualTo(savedComment2.getWriter().getNickname());
        assertThat(comment2.getMemberSeq()).isEqualTo(savedComment2.getWriter().getSeq());
        assertThat(comment2.getContent()).isEqualTo(savedComment2.getContent());
        assertThat(comment2.getCreatedDate()).isEqualTo(savedComment2.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    @DisplayName("공지사항 댓글 조회 - 예외 처리 : 공지사항 데이터 없을 때")
    public void viewNoticeCommentFailWhenNoticeNull() {
        assertThatThrownBy(() -> noticeCommentService.getComments(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_NOTICE);
    }
}
