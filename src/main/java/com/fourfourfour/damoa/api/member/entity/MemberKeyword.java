package com.fourfourfour.damoa.api.user.entity;

import com.fourfourfour.damoa.api.keyword.entity.Keyword;
import com.fourfourfour.damoa.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Entity
public class MemberKeyword extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_keyword_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

}
