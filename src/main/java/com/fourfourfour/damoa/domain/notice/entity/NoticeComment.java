package com.fourfourfour.damoa.domain.notice.entity;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.common.entity.BaseLastModifiedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "content", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_notice_comment")
@Entity
public class NoticeComment extends BaseLastModifiedEntity {

    @Id @GeneratedValue
    @Column(name = "comment_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "notice_seq", nullable = false)
    private Notice notice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_seq", nullable = false)
    private Member writer;

    @Builder
    public NoticeComment(String content, Notice notice, Member writer) {
        this.content = content;
        this.isDeleted = false;
        this.notice = notice;
        this.writer = writer;
    }
}
