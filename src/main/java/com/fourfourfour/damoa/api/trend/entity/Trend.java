package com.fourfourfour.damoa.api.trend.entity;

import com.fourfourfour.damoa.api.trend.enums.TrendTitle;
import com.fourfourfour.damoa.common.entity.BaseLastModifiedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static lombok.AccessLevel.PROTECTED;

@ToString(of = {"seq", "trendTitle", "data", "isDeleted"})
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_trend")
@Entity
public class Trend extends BaseLastModifiedEntity {

    @Id @GeneratedValue
    @Column(name = "trend_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Enumerated(value = STRING)
    @Column(nullable = false, length = 80)
    private TrendTitle trendTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String data;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isDeleted;

    @Builder
    public Trend(TrendTitle trendTitle, String data) {
        this.trendTitle = trendTitle;
        this.data = data;
        this.isDeleted = false;
    }

    public void changeData(String data) {
        this.data = data;
    }
}
