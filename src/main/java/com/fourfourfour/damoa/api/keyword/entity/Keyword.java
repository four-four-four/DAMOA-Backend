package com.fourfourfour.damoa.api.keyword.entity;

import com.fourfourfour.damoa.api.moa.entity.Moa;
import com.fourfourfour.damoa.common.entity.BaseCreatedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "name", "isFiltered", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_keyword")
@Entity
public class Keyword extends BaseCreatedEntity {

    @Id @GeneratedValue
    @Column(name = "keyword_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isFiltered;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @OneToMany(mappedBy = "keyword", cascade = ALL)
    private List<MemberKeyword> memberKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "keyword", cascade = ALL)
    private List<Moa> moas = new ArrayList<>();

    @Builder
    public Keyword(String name) {
        this.name = name;
        this.isFiltered = false;
        this.isDeleted = false;
    }
}
