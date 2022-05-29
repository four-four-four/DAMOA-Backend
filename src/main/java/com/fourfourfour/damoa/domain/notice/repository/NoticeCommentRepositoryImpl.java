package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.notice.service.dto.NoticeCommentDto;
import com.fourfourfour.damoa.domain.notice.service.dto.QNoticeCommentDto_Detail;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.fourfourfour.damoa.domain.notice.entity.QNoticeComment.*;

public class NoticeCommentRepositoryImpl implements NoticeCommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public NoticeCommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<NoticeCommentDto.Detail>> findCommentsByNoticeSeq(Long noticeSeq) {
        List<NoticeCommentDto.Detail> commentList = queryFactory
                .select(new QNoticeCommentDto_Detail(
                        noticeComment.seq,
                        noticeComment.content,
                        noticeComment.createdDate,
                        noticeComment.writer.seq,
                        noticeComment.writer.nickname
                ))
                .from(noticeComment)
                .where(
                        noticeComment.notice.seq.eq(noticeSeq),
                        noticeComment.isDeleted.isFalse()
                )
                .orderBy(
                        noticeComment.createdDate.asc()
                ).fetch();

        if (commentList.size() == 0) {
            commentList = null;
        }

        return Optional.ofNullable(commentList);
    }
}
