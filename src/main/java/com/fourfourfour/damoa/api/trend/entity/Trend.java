package com.fourfourfour.damoa.api.trend.entity;

import com.fourfourfour.damoa.common.entity.BaseDeletedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_trend")
@Entity
public class Trend extends BaseDeletedEntity {

    @Id @GeneratedValue
    @Column(name = "trend_id")
    private Long id;

    @Column(nullable = false, length = 80)
    private String feature;

    @Column(nullable = false)
    private String data;

}
