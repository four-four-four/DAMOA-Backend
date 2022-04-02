package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.entity.NoticeComment;
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
}
