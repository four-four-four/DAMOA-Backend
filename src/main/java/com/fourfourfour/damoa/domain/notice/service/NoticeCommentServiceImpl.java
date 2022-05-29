package com.fourfourfour.damoa.domain.notice.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.notice.entity.Notice;
import com.fourfourfour.damoa.domain.notice.entity.NoticeComment;
import com.fourfourfour.damoa.domain.notice.repository.NoticeCommentRepository;
import com.fourfourfour.damoa.domain.notice.repository.NoticeRepository;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeCommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NoticeCommentServiceImpl implements NoticeCommentService {

    private final MemberRepository memberRepository;

    private final NoticeRepository noticeRepository;

    private final NoticeCommentRepository noticeCommentRepository;

    @Override
    @Transactional
    public NoticeComment register(NoticeCommentDto.RegisterDto registerDto, Long memberSeq) {
        Notice noticeSeq = noticeRepository.findBySeq(registerDto.getNoticeSeq())
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_NOTICE));

        NoticeComment newComment = NoticeComment.builder()
                .content(registerDto.getContent())
                .writer(memberRepository.findBySeq(memberSeq).get())
                .notice(noticeSeq)
                .build();

        return noticeCommentRepository.save(newComment);
    }

    @Override
    public List<NoticeCommentDto.Detail> getComments(Long noticeSeq) {
        Notice findNotice = noticeRepository.findBySeq(noticeSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_NOTICE));

        List<NoticeCommentDto.Detail> noticeComments = new ArrayList<>();
        List<NoticeComment> comments = findNotice.getComments();

        for (NoticeComment comment : comments) {
            if (!comment.isDeleted()) {
                noticeComments.add(
                        NoticeCommentDto.Detail.builder()
                                .commentSeq(comment.getSeq())
                                .content(comment.getContent())
                                .createdDate(comment.getCreatedDate())
                                .memberSeq(comment.getWriter().getSeq())
                                .writer(comment.getWriter().getNickname())
                                .build()
                );
            }
        }

        return noticeComments;
    }

    @Override
    @Transactional
    public void deleteAll() {
        noticeCommentRepository.deleteAll();
    }
}
