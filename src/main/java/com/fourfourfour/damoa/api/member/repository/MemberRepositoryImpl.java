package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.dto.res.QResMemberDto;
import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;
import com.fourfourfour.damoa.common.util.LogUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;

import static com.fourfourfour.damoa.api.member.entity.QMember.*;

@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ResMemberDto findResMemberDtoByEmail(String email) {
        log.info(LogUtil.getClassAndMethodName());

        return queryFactory
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
                .fetchOne();
    }

}
