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

    private MemberRequestDto.RegisterDto adminRegisterDto, memberRegisterDto;

    private NoticeRequestDto.RegisterDto noticeRegisterDto;

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

        memberRegisterDto = MemberRequestDto.RegisterDto.builder()
                .email("test2@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender("male")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("전문직")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        noticeRegisterDto = NoticeRequestDto.RegisterDto.builder()
                .title("공지사항 제목")
                .content("공지사항 본문")
                .build();
    }

    @Test
    @DisplayName("공지사항 댓글 작성 - 성공")
    public void commentRegisterSuccess() {
        // 회원가입
        Member savedAdmin = memberService.register(adminRegisterDto.toServiceDto());
        Member savedMember = memberService.register(memberRegisterDto.toServiceDto());

        // 공지사항 등록
        Notice savedNotice = noticeService.register(noticeRegisterDto.toServiceDto(), savedAdmin.getSeq());

        // 작성된 공지사항 댓글 작성
        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto = NoticeCommentRequestDto.RegisterDto.builder()
                .noticeSeq(savedNotice.getSeq())
                .content("DAMOA 공지사항 댓글")
                .build();

        NoticeComment savedNoticeComment = noticeCommentService.register(noticeCommentRegisterDto.toServiceDto(), savedMember.getSeq());
        em.flush();
        em.clear();

        // 작성된 댓글 검증
        Optional<NoticeComment> findNoticeComment = noticeCommentRepository.findBySeq(savedNoticeComment.getSeq());
        assertThat(noticeCommentRegisterDto.getNoticeSeq()).isEqualTo(findNoticeComment.get().getNotice().getSeq());
        assertThat(noticeCommentRegisterDto.getContent()).isEqualTo(findNoticeComment.get().getContent());
    }

    @Test
    @DisplayName("공지사항 댓글 작성 - 예외 처리 : 공지사항 데이터 없을 때")
    public void commentRegisterFailWhenNoticeNull() {

        Member savedMember = memberService.register(memberRegisterDto.toServiceDto());

        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto = NoticeCommentRequestDto.RegisterDto.builder()
                .noticeSeq(0L)
                .content("DAMOA 공지사항 댓글")
                .build();

        assertThatThrownBy(() -> noticeCommentService.register(noticeCommentRegisterDto.toServiceDto(), savedMember.getSeq()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_NOTICE);
    }
}
