package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.notice.entity.NoticeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NoticeCommentRepository extends JpaRepository<NoticeComment, Long>, NoticeCommentRepositoryCustom {
    @Query("SELECT nc FROM NoticeComment nc WHERE nc.seq = :seq AND nc.isDeleted = FALSE")
    Optional<NoticeComment> findBySeq(@Param("seq") Long seq);
}