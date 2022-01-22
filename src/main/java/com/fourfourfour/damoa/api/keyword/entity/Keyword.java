package com.fourfourfour.damoa.api.keyword.entity;

import com.fourfourfour.damoa.common.entity.BaseDeletedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "tb_keyword")
@Entity
public class Keyword extends BaseDeletedEntity {

    @Id @GeneratedValue
    @Column(name = "keyword_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany
    @JoinColumn(name = "collected_data_id")
    private List<CollectedData> collectedData = new ArrayList<>();

}
