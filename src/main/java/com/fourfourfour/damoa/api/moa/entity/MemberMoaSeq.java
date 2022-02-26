package com.fourfourfour.damoa.api.moa.entity;

import java.io.Serializable;
import java.util.Objects;

public class MemberMoaSeq implements Serializable {

    private Long member;
    private Long moa;

    @Override
    public int hashCode() {
        return Objects.hash(member, moa);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MemberMoaSeq that = (MemberMoaSeq) obj;
        return Objects.equals(member, that.member) && Objects.equals(moa, that.moa);
    }
}
