package com.fourfourfour.damoa.api.keyword.entity;

import com.fourfourfour.damoa.common.entity.BaseDeletedEntity;
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
@Table(name = "tb_collected_data")
@Entity
public class CollectedData extends BaseDeletedEntity {

    @Id @GeneratedValue
    @Column(name = "collected_data_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String url;

    private String imgUrl;

    @ManyToOne(fetch = LAZY)
    private Keyword keyword;

}
