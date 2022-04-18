package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.notice.service.dto.NoticeResponseDto;
import com.fourfourfour.damoa.domain.notice.service.dto.QNoticeResponseDto_ForPage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.fourfourfour.damoa.domain.notice.entity.QNotice.notice;

public class NoticeRepositoryImpl implements NoticeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public NoticeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countAllNotice() {
        return queryFactory
                .select(notice.count().intValue())
                .from(notice)
                .where(notice.isDeleted.isFalse())
                .fetchOne();
    }

    @Override
    public Optional<List<NoticeResponseDto.ForPage>> findNoticeForPage(Pageable pageable) {
        List<NoticeResponseDto.ForPage> noticeList = queryFactory
                .select(new QNoticeResponseDto_ForPage(
                        notice.seq,
                        notice.title,
                        notice.views,
                        notice.writer.nickname
                ))
                .from(notice)
                .where(notice.isDeleted.isFalse())
                .orderBy(notice.createdDate.desc())
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        if (noticeList.size() == 0) {
            noticeList = null;
        }

        return Optional.ofNullable(noticeList);
    }
}
