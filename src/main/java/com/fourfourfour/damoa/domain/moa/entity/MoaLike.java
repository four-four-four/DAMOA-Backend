package com.fourfourfour.damoa.domain.moa.entity;

import com.fourfourfour.damoa.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@IdClass(MemberMoaSeq.class)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_moa_like")
@Entity
public class MoaLike {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_seq", columnDefinition = "BIGINT UNSIGNED")
    private Member member;

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "moa_seq", columnDefinition = "BIGINT UNSIGNED")
    private Moa moa;

    @Builder
    public MoaLike(Member member, Moa moa) {
        this.member = member;
        this.moa = moa;
    }
}
