package com.fourfourfour.damoa.domain.member.entity;

import com.fourfourfour.damoa.domain.event.entity.Event;
import com.fourfourfour.damoa.domain.event.entity.EventComment;
import com.fourfourfour.damoa.domain.keyword.entity.KeywordComment;
import com.fourfourfour.damoa.domain.keyword.entity.MemberKeyword;
import com.fourfourfour.damoa.domain.moa.entity.MoaLike;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.entity.NoticeComment;
import com.fourfourfour.damoa.common.entity.BaseLastModifiedEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static lombok.AccessLevel.*;

@ToString(of = {"seq", "email", "password", "nickname", "gender", "birthDate", "job", "serviceTerm", "privacyTerm", "role", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "tb_member",
        uniqueConstraints = {
                @UniqueConstraint(name = "email_unique", columnNames = "email")
        }
)
@Entity
public class Member extends BaseLastModifiedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 80)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Gender gender;

    @Column(columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(length = 50)
    private String job;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean serviceTerm;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean privacyTerm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<MemberKeyword> memberKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private List<KeywordComment> keywordComments = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private List<NoticeComment> noticeComments = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private List<EventComment> eventComments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<MoaLike> moaLikes = new ArrayList<>();

    @Builder
    public Member(String email, String password, String nickname, Gender gender, LocalDate birthDate, String job, boolean serviceTerm, boolean privacyTerm, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.job = job;
        this.serviceTerm = serviceTerm;
        this.privacyTerm = privacyTerm;
        this.role = role;
        this.isDeleted = false;
    }

    public enum Gender {

        MALE("남성"), FEMALE("여성"), ETC("기타");

        private final String description;

        Gender(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum Role implements GrantedAuthority {

        MEMBER(ROLES.MEMBER, "회원"),
        ADMIN(Role.ROLES.ADMIN, "관리자");

        private final String authority;
        private final String description;

        Role(String authority, String description) {
            this.authority = authority;
            this.description = description;
        }

        @Override
        public String getAuthority() {
            return authority;
        }

        public String getDescription() {
            return description;
        }

        public static class ROLES {
            public static final String MEMBER = "ROLE_MEMBER";
            public static final String ADMIN = "ROLE_ADMIN";
        }
    }
}
