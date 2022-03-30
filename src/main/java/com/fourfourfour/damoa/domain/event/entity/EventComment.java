package com.fourfourfour.damoa.domain.event.entity;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.common.entity.BaseLastModifiedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "content", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_event_comment")
@Entity
public class EventComment extends BaseLastModifiedEntity {

    @Id @GeneratedValue
    @Column(name = "comment_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_seq", nullable = false)
    private Event event;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_seq", nullable = false)
    private Member writer;

    @Builder
    public EventComment(String content, Event event, Member writer) {
        this.content = content;
        this.isDeleted = false;
        this.event = event;
        this.writer = writer;
    }
}
