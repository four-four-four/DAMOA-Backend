package com.fourfourfour.damoa.api.moa.entity;

import com.fourfourfour.damoa.api.keyword.entity.Keyword;
import com.fourfourfour.damoa.common.entity.BaseCreatedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "title", "content", "source", "url", "imgUrl", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_moa")
@Entity
public class Moa extends BaseCreatedEntity {

    @Id @GeneratedValue
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
