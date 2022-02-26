package com.fourfourfour.damoa.api.keyword.entity;

import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.common.entity.BaseLastModifiedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "content", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_keyword_comment")
@Entity
public class KeywordComment extends BaseLastModifiedEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "keyword_seq", nullable = false)
    private Keyword keyword;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_seq", nullable = false)
    private Member writer;
}
