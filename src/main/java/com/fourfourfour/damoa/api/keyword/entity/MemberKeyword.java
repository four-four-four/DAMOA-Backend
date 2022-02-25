package com.fourfourfour.damoa.api.member.entity;

import com.fourfourfour.damoa.api.keyword.entity.Keyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@IdClass(MemberKeywordSeq.class)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_member_keyword")
@Entity
public class MemberKeyword {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    private Member member;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "keyword_seq", columnDefinition = "BIGINT UNSIGNED")
    private Keyword keyword;

    @Builder
    public MemberKeyword(Member member, Keyword keyword) {
        this.member = member;
        this.keyword = keyword;
    }
}
