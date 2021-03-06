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
         * ????????? ???????????? ????????? ????????? ?????????
         * adminRegisterDto == ?????????
         * memberRegisterDto == ??????
         * ?????? ?????? ???????????????.
         */

        adminRegisterDto1 = MemberRequestDto.RegisterDto.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender("female")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("?????????")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        memberRegisterDto1 = MemberRequestDto.RegisterDto.builder()
                .email("test2@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender("male")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("?????????")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        noticeRegisterDto1 = NoticeRequestDto.RegisterDto.builder()
                .title("???????????? ??????")
                .content("???????????? ??????")
                .build();
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ??????")
    public void commentRegisterSuccess() {
        // ????????????
        Member savedAdmin = memberService.register(adminRegisterDto1.toServiceDto());
        Member savedMember = memberService.register(memberRegisterDto1.toServiceDto());

        // ???????????? ??????
        Notice savedNotice = noticeService.register(noticeRegisterDto1.toServiceDto(), savedAdmin.getSeq());

        // ????????? ???????????? ?????? ??????
        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto = NoticeCommentRequestDto.RegisterDto.builder()
                .content("DAMOA ???????????? ??????")
                .build();

        NoticeComment savedNoticeComment = noticeCommentService.register(noticeCommentRegisterDto.toServiceDto(savedNotice.getSeq()), savedMember.getSeq());
        em.flush();
        em.clear();

        // ????????? ?????? ??????
        Optional<NoticeComment> findNoticeComment = noticeCommentRepository.findBySeq(savedNoticeComment.getSeq());
        assertThat(noticeCommentRegisterDto.getContent()).isEqualTo(findNoticeComment.get().getContent());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ?????? ?????? : ???????????? ????????? ?????? ???")
    public void commentRegisterFailWhenNoticeNull() {

        Member savedMember = memberService.register(memberRegisterDto1.toServiceDto());

        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto = NoticeCommentRequestDto.RegisterDto.builder()
                .content("DAMOA ???????????? ??????")
                .build();

        assertThatThrownBy(() -> noticeCommentService.register(noticeCommentRegisterDto.toServiceDto(0L), savedMember.getSeq()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_NOTICE);
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ??????")
    public void viewNoticeCommentSuccess() throws InterruptedException {
        Member savedAdmin = memberService.register(adminRegisterDto1.toServiceDto());
        Member savedMember = memberService.register(memberRegisterDto1.toServiceDto());

        Notice savedNotice = noticeService.register(noticeRegisterDto1.toServiceDto(), savedAdmin.getSeq());

        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto1 = NoticeCommentRequestDto.RegisterDto.builder()
                .content("???????????? ?????? 1")
                .build();

        NoticeCommentRequestDto.RegisterDto noticeCommentRegisterDto2 = NoticeCommentRequestDto.RegisterDto.builder()
                .content("???????????? ?????? 2")
                .build();

        NoticeComment savedComment1 = noticeCommentService.register(noticeCommentRegisterDto1.toServiceDto(savedNotice.getSeq()), savedMember.getSeq());
        Thread.sleep(1000);
        NoticeComment savedComment2 = noticeCommentService.register(noticeCommentRegisterDto2.toServiceDto(savedNotice.getSeq()), savedMember.getSeq());

        em.flush();
        em.clear();

        List<NoticeCommentDto.Detail> noticeComments = noticeCommentService.getComments(savedNotice.getSeq());
        assertThat(noticeComments.size()).isEqualTo(2);

        NoticeCommentDto.Detail comment1 = noticeComments.get(0);
        assertThat(comment1.getCommentSeq()).isEqualTo(savedComment1.getSeq());
        assertThat(comment1.getWriter()).isEqualTo(savedComment1.getWriter().getNickname());
        assertThat(comment1.getMemberSeq()).isEqualTo(savedComment1.getWriter().getSeq());
        assertThat(comment1.getContent()).isEqualTo(savedComment1.getContent());

        NoticeCommentDto.Detail comment2 = noticeComments.get(1);
        assertThat(comment2.getCommentSeq()).isEqualTo(savedComment2.getSeq());
        assertThat(comment2.getWriter()).isEqualTo(savedComment2.getWriter().getNickname());
        assertThat(comment2.getMemberSeq()).isEqualTo(savedComment2.getWriter().getSeq());
        assertThat(comment2.getContent()).isEqualTo(savedComment2.getContent());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? - ?????? ?????? : ???????????? ????????? ?????? ???")
    public void viewNoticeCommentFailWhenNoticeNull() {
        assertThatThrownBy(() -> noticeCommentService.getComments(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_NOTICE);
    }
}
