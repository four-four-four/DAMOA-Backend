package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.entity.NoticeComment;
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
public class NoticeCommentRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeCommentRepository noticeCommentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Member admin1, member1;

    private Notice notice1;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();
        noticeRepository.deleteAll();
        noticeCommentRepository.deleteAll();
        em.flush();
        em.clear();

        /**
         * 관리자 프로젝트 구성이 완료될 때까지
         * Member를 이용하여 생성합니다.
         */

        admin1 = Member.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender(Member.Gender.FEMALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("학생")
                .role(Member.Role.ADMIN)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        member1 = Member.builder()
                .email("test2@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender(Member.Gender.MALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("전문직")
                .role(Member.Role.MEMBER)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        notice1 = Notice.builder()
                .title("공지사항 제목")
                .content("공지사항 본문")
                .writer(admin1)
                .build();
    }

    @Test
    @DisplayName("공지사항 댓글 엔티티 등록")
    public void commentRegister() {
        // 회원가입
        memberRepository.save(admin1);
        memberRepository.save(member1);

        // 공지사항 엔티티 저장
        noticeRepository.save(notice1);

        // 공지사항 댓글 엔티티 저장
        NoticeComment noticeComment = NoticeComment.builder()
                .notice(notice1)
                .writer(member1)
                .content("DAMOA 공지사항 댓글")
                .build();

        noticeCommentRepository.save(noticeComment);
        em.flush();
        em.clear();

        // 작성된 공지사항 댓글 엔티티 검증
        Optional<NoticeComment> savedNoticeComment = noticeCommentRepository.findBySeq(noticeComment.getSeq());
        assertThat(noticeComment.getSeq()).isEqualTo(savedNoticeComment.get().getSeq());
        assertThat(noticeComment.getNotice().getSeq()).isEqualTo(savedNoticeComment.get().getNotice().getSeq());
        assertThat(noticeComment.getContent()).isEqualTo(savedNoticeComment.get().getContent());
        assertThat(noticeComment.isDeleted()).isEqualTo(savedNoticeComment.get().isDeleted());
    }

    @Test
    @DisplayName("공지사항 댓글 조회 : 성공")
    public void findNoticeCommentSuccess() throws InterruptedException {
        // 관리자와 사용자 회원가입
        memberRepository.save(admin1);
        memberRepository.save(member1);

        noticeRepository.save(notice1);

        // 공지사항 댓글 엔티티 저장
        NoticeComment noticeComment1 = NoticeComment.builder()
                .notice(notice1)
                .writer(member1)
                .content("DAMOA 공지사항 댓글 1")
                .build();

        NoticeComment noticeComment2 = NoticeComment.builder()
                .notice(notice1)
                .writer(member1)
                .content("DAMOA 공지사항 댓글 2")
                .build();

        noticeCommentRepository.save(noticeComment1);
        Thread.sleep(1000);
        noticeCommentRepository.save(noticeComment2);

        em.flush();
        em.clear();

        List<NoticeCommentDto.Detail> findNoticeComment = noticeCommentRepository.findCommentsByNoticeSeq(notice1.getSeq())
                .orElse(null);
        assertThat(findNoticeComment).isNotNull();
        assertThat(findNoticeComment.size()).isEqualTo(2);
        NoticeCommentDto.Detail comment1 = findNoticeComment.get(0);
        assertThat(comment1.getCommentSeq()).isEqualTo(noticeComment1.getSeq());
        assertThat(comment1.getContent()).isEqualTo(noticeComment1.getContent());
        assertThat(comment1.getMemberSeq()).isEqualTo(noticeComment1.getWriter().getSeq());
        assertThat(comment1.getWriter()).isEqualTo(noticeComment1.getWriter().getNickname());

        NoticeCommentDto.Detail comment2 = findNoticeComment.get(1);
        assertThat(comment2.getCommentSeq()).isEqualTo(noticeComment2.getSeq());
        assertThat(comment2.getContent()).isEqualTo(noticeComment2.getContent());
        assertThat(comment2.getMemberSeq()).isEqualTo(noticeComment2.getWriter().getSeq());
        assertThat(comment2.getWriter()).isEqualTo(noticeComment2.getWriter().getNickname());
    }
}
