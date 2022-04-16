package com.fourfourfour.damoa.domain.event.entity;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.common.entity.BaseLastModifiedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "title", "content", "views", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_event")
@Entity
public class Event extends BaseLastModifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_seq")
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer views;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_seq", nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "event", cascade = ALL)
    private List<EventComment> comments = new ArrayList<>();

    public Event(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.views = 0;
        this.isDeleted = false;
        this.writer = writer;
    }
}
