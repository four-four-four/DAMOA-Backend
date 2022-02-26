package com.fourfourfour.damoa.api.member.entity;

import com.fourfourfour.damoa.api.event.entity.Event;
import com.fourfourfour.damoa.api.event.entity.EventComment;
import com.fourfourfour.damoa.api.keyword.entity.KeywordComment;
import com.fourfourfour.damoa.api.keyword.entity.MemberKeyword;
import com.fourfourfour.damoa.api.member.enums.Gender;
import com.fourfourfour.damoa.api.member.enums.Role;
import com.fourfourfour.damoa.api.moa.entity.MoaLike;
import com.fourfourfour.damoa.api.notice.entity.Notice;
import com.fourfourfour.damoa.api.notice.entity.NoticeComment;
import com.fourfourfour.damoa.common.entity.BaseLastModifiedEntity;
import lombok.*;

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

    @Id @GeneratedValue
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
}
