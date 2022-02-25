package com.fourfourfour.damoa.api.trend.entity;

import com.fourfourfour.damoa.common.entity.BaseLastModifiedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "feature", "data", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_trend")
@Entity
public class Trend extends BaseLastModifiedEntity {

    @Id @GeneratedValue
    @Column(name = "trend_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false, length = 80)
    private String feature;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String data;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @Builder
    public Trend(String feature, String data) {
        this.feature = feature;
        this.data = data;
        this.isDeleted = false;
    }
}
