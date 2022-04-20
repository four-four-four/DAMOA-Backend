package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
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

    private Member member1, member2;

    private Notice notice1, notice2, notice3, notice4, notice5;

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

        member2 = Member.builder()
                .email("test3@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender(Member.Gender.FEMALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .role(Member.Role.MEMBER)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        String title = "damoaTitleTest";
        String content = "damoaContentTest";

        notice1 = Notice.builder()
                .title(title)
                .content(content)
                .writer(member1)
                .build();

        notice2 = Notice.builder()
                .title(title + "2")
                .content(content + "2")
                .writer(member1)
                .build();

        notice3 = Notice.builder()
                .title(title + "3")
                .content(content + "3")
                .writer(member1)
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

    @Test
    @DisplayName("공지사항 목록 조회 - 공지사항 개수 : 성공")
    public void countAllNoticeSuccess() {
        // 관리자와 사용자 회원가입
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 공지사항 엔티티 저장
        noticeRepository.save(notice1);
        noticeRepository.save(notice2);
        noticeRepository.save(notice3);

        em.flush();
        em.clear();

        // 공지사항 개수 조회 검증
        Integer findNotices = noticeRepository.countAllNotice();
        assertThat(findNotices).isEqualTo(3);
    }

    @Test
    @DisplayName("공지사항 목록 조회 : 성공")
    public void findNoticeForPageSuccess() throws InterruptedException {
        // 공지사항 목록 존재하지 않을 때 검증
        Pageable pageable = PageRequest.of(1, 3);
        List<NoticeResponseDto.ForPage> page1 = noticeRepository.findNoticeForPage(pageable)
                .orElse(null);
        assertThat(page1).isNull();

        // 관리자와 사용자 회원가입
        Member savedAdmin = memberRepository.save(member1);
        memberRepository.save(member2);

        // 공지사항 엔티티 저장
        noticeRepository.save(notice1);
        Thread.sleep(1000);
        noticeRepository.save(notice2);
        Thread.sleep(1000);
        noticeRepository.save(notice3);
        Thread.sleep(1000);

        em.flush();
        em.clear();

        // 작성된 공지사항 페이징 및 엔티티 검증
        List<NoticeResponseDto.ForPage> page2 = noticeRepository.findNoticeForPage(pageable)
                .orElse(null);
        assertThat(page2).isNotNull();
        assertThat(page2.size()).isEqualTo(3);

        NoticeResponseDto.ForPage noticeForPage1 = page2.get(0);
        assertThat(noticeForPage1.getTitle()).isEqualTo(notice3.getTitle());
        assertThat(noticeForPage1.getViews()).isEqualTo(0);
        assertThat(noticeForPage1.getWriter()).isEqualTo(savedAdmin.getNickname());
        assertThat(noticeForPage1.getSeq()).isEqualTo(notice3.getSeq());

        NoticeResponseDto.ForPage noticeForPage2 = page2.get(1);
        assertThat(noticeForPage2.getTitle()).isEqualTo(notice2.getTitle());
        assertThat(noticeForPage2.getViews()).isEqualTo(0);
        assertThat(noticeForPage2.getWriter()).isEqualTo(savedAdmin.getNickname());
        assertThat(noticeForPage2.getSeq()).isEqualTo(notice2.getSeq());

        NoticeResponseDto.ForPage noticeForPage3 = page2.get(2);
        assertThat(noticeForPage3.getTitle()).isEqualTo(notice1.getTitle());
        assertThat(noticeForPage3.getViews()).isEqualTo(0);
        assertThat(noticeForPage3.getWriter()).isEqualTo(savedAdmin.getNickname());
        assertThat(noticeForPage3.getSeq()).isEqualTo(notice1.getSeq());
    }
}
