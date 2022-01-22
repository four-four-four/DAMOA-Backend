package com.fourfourfour.damoa.api.member.entity;

import com.fourfourfour.damoa.api.notice.entity.Notice;
import com.fourfourfour.damoa.common.entity.BaseDeletedEntity;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_member", uniqueConstraints = {@UniqueConstraint(name = "email_unique", columnNames = "email")})
@Entity
public class Member extends BaseDeletedEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, length = 80)
    private String nickname;

    @Column(length = 20)
    private String gender;

    private LocalDateTime birthDate;

    @Column(length = 50)
    private String job;

    @Column(nullable = false)
    private boolean serviceTerm;

    @Column(nullable = false)
    private boolean privacyTerm;

    @Column(nullable = false, length = 20)
    private String role;

    @OneToMany(mappedBy = "member")
    private List<MemberKeyword> memberKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

}
