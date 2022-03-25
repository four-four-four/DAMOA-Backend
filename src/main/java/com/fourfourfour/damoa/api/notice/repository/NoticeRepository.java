package com.fourfourfour.damoa.api.notice.repository;

import com.fourfourfour.damoa.api.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.seq = :seq")
    Notice findBySeq(@Param("seq") Long seq);
}
