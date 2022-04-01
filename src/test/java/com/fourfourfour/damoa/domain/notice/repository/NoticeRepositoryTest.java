package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
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
public class NoticeRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    private Member member1;

    @BeforeEach
    public void setUp() {

        memberRepository.deleteAll();
        noticeRepository.deleteAll();
        em.flush();
        em.clear();

        member1 = Member.builder()
                .email("test2@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender(Member.Gender.FEMALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .role(Member.Role.ADMIN)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

    }

    @Test
    @DisplayName("공지사항 엔티티 등록 : 성공")
    public void noticeRegister() {
        memberRepository.save(member1);

        Notice notice = Notice.builder()
                .title("DAMOA 공지사항 제목")
                .content("DAMOA 공지사항 본문")
                .writer(member1)
                .build();

        noticeRepository.save(notice);
        em.flush();
        em.clear();

        Optional<Notice> savedNotice = noticeRepository.findBySeq(notice.getSeq());
        assertThat(notice.getSeq()).isEqualTo(savedNotice.get().getSeq());
        assertThat(notice.getTitle()).isEqualTo(savedNotice.get().getTitle());
        assertThat(notice.getContent()).isEqualTo(savedNotice.get().getContent());
        assertThat(notice.getViews()).isEqualTo(savedNotice.get().getViews());
        assertThat(notice.getWriter().getSeq()).isEqualTo(savedNotice.get().getWriter().getSeq());
        assertThat(notice.isDeleted()).isEqualTo(savedNotice.get().isDeleted());
    }
}
