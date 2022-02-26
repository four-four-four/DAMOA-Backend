package com.fourfourfour.damoa.api.keyword.entity;

import java.io.Serializable;
import java.util.Objects;

public class MemberKeywordSeq implements Serializable {

    private Long member;
    private Long keyword;

    @Override
    public int hashCode() {
        return Objects.hash(member, keyword);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MemberKeywordSeq that = (MemberKeywordSeq) obj;
        return Objects.equals(member, that.member) && Objects.equals(keyword, that.keyword);
    }
}
