package com.fourfourfour.damoa.domain.keyword.entity;

import com.fourfourfour.damoa.common.entity.BaseCreatedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "tb_banned_keyword",
        uniqueConstraints = {
                @UniqueConstraint(name = "name_unique", columnNames = "name")
        }
)
@Getter
@ToString(of = {"seq", "name"})
@NoArgsConstructor(access = PROTECTED)
public class BannedKeyword extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banned_keyword_seq", columnDefinition = "BIGINT UNSIGNED")
    private Long seq;

    @Column(nullable = false)
    private String name;

    @Builder
    public BannedKeyword(Long seq, String name) {
        this.seq = seq;
        this.name = name;
    }
}
