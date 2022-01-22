package com.fourfourfour.damoa.api.user.entity;

import com.fourfourfour.damoa.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Table(uniqueConstraints = {@UniqueConstraint(name = "email_unique", columnNames = "email")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member extends BaseEntity {

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

    private LocalDate birthDate;

    @Column(length = 50)
    private String job;

    @Column(nullable = false)
    private boolean serviceTerm;

    @Column(nullable = false)
    private boolean privacyTerm;

    @Column(nullable = false, length = 20)
    private String role;

    private LocalDateTime withdrawnDate;

    @OneToMany(mappedBy = "member")
    private List<MemberKeyword> memberKeywords = new ArrayList<>();

}
