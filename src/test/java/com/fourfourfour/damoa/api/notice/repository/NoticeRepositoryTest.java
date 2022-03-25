package com.fourfourfour.damoa.api.notice.repository;

import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.enums.Gender;
import com.fourfourfour.damoa.api.member.enums.Role;
import com.fourfourfour.damoa.api.member.repository.MemberRepository;
import com.fourfourfour.damoa.api.notice.entity.Notice;
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
public class NoticeRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    private Member member;

    @BeforeEach
    public void setUp() {

        memberRepository.deleteAll();
        noticeRepository.deleteAll();
        em.flush();
        em.clear();

        member = Member.builder()
                .email("test2@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .role(Role.ADMIN)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

    }

    @Test
    @DisplayName("공지사항 엔티티 등록 테스트")
    public void register() {
        memberRepository.save(member);

        Notice notice = Notice.builder()
                .title("DAMOA 공지사항 제목")
                .content("DAMOA 공지사항 본문")
                .writer(member)
                .build();

        noticeRepository.save(notice);

        Notice savedNotice = noticeRepository.findBySeq(notice.getSeq());
        assertThat(notice.getSeq()).isEqualTo(savedNotice.getSeq());
        assertThat(notice.getTitle()).isEqualTo(savedNotice.getTitle());
        assertThat(notice.getContent()).isEqualTo(savedNotice.getContent());
        assertThat(notice.getViews()).isEqualTo(savedNotice.getViews());
        assertThat(notice.getWriter()).isEqualTo(savedNotice.getWriter());
        assertThat(notice.isDeleted()).isEqualTo(savedNotice.isDeleted());
    }
}
