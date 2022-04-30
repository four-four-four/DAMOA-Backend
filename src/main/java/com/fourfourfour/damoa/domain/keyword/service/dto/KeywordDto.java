package com.fourfourfour.damoa.domain.keyword.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class KeywordDto {

    @Getter
    @ToString(of = {"seq", "name"})
    public static class BasicDto {

        private final Long seq;

        private final String name;

        @Builder
        public BasicDto(Long seq, String name) {
            this.seq = seq;
            this.name = name;
        }
    }
}
