package com.fourfourfour.damoa.domain.moa.entity;

import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import com.fourfourfour.damoa.common.entity.BaseCreatedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "title", "content", "source", "url", "imgUrl", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_moa")
@Entity
public class Moa extends BaseCreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moa_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String imgUrl;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "keyword_seq", nullable = false)
    private Keyword keyword;

    @OneToMany(mappedBy = "moa", cascade = ALL)
    private List<MoaLike> moaLikes = new ArrayList<>();

    @Builder
    public Moa(String title, String content, String source, String url, String imgUrl, Keyword keyword) {
        this.title = title;
        this.content = content;
        this.source = source;
        this.url = url;
        this.imgUrl = imgUrl;
        this.isDeleted = false;
        this.keyword = keyword;
    }
}
