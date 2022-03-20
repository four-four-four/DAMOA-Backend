package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.dto.res.QResMemberDto;
import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;

import java.util.Optional;

import static com.fourfourfour.damoa.api.member.entity.QMember.*;

@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<ResMemberDto> findResMemberDtoByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .select(new QResMemberDto(
                        member.seq,
                        member.email,
                        member.password,
                        member.nickname,
                        member.gender,
                        member.birthDate,
                        member.job,
                        member.role))
                .from(member)
                .where(member.email.eq(email))
                .fetchOne());
    }
}
