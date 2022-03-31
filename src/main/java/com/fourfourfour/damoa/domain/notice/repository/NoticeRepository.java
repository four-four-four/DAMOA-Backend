package com.fourfourfour.damoa.domain.notice.repository;

import com.fourfourfour.damoa.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.seq = :seq AND n.isDeleted = FALSE")
    Notice findBySeq(@Param("seq") Long seq);
}