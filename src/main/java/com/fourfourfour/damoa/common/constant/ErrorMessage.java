package com.fourfourfour.damoa.common.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessage {

    public static String AUTHENTICATION_MEMBER;

    public static String PATTERN;

    public static String PATTERN_MEMBER_EMAIL;

    public static String PATTERN_MEMBER_PASSWORD;

    public static String PATTERN_MEMBER_NICKNAME;

    public static String PATTERN_MEMBER_TERM;

    public static String NULL_MEMBER;

    public static String NULL_MEMBER_EMAIL;

    public static String NULL_NOTICE;

    public static String BLANK_NOTICE_TITLE;

    public static String BLANK_NOTICE_CONTENT;

    public static String BLANK_NOTICE_COMMENT;

    public static String FORBIDDEN;

    public static String ERROR;

    @Autowired
    public ErrorMessage(MessageSource messageSource) {
        AUTHENTICATION_MEMBER = messageSource.getMessage("authentication.member", null, null);
        PATTERN = messageSource.getMessage("pattern", null, null);
        PATTERN_MEMBER_EMAIL = messageSource.getMessage("pattern.member.email", null, null);
        PATTERN_MEMBER_PASSWORD = messageSource.getMessage("pattern.member.password", null, null);
        PATTERN_MEMBER_NICKNAME = messageSource.getMessage("pattern.member.nickname", null, null);
        PATTERN_MEMBER_TERM = messageSource.getMessage("pattern.member.term", null, null);
        NULL_MEMBER = messageSource.getMessage("null.member", null, null);
        NULL_MEMBER_EMAIL = messageSource.getMessage("null.member.email", null, null);
        NULL_NOTICE = messageSource.getMessage("null.notice", null, null);
        BLANK_NOTICE_TITLE = messageSource.getMessage("blank.notice.title", null, null);
        BLANK_NOTICE_CONTENT = messageSource.getMessage("blank.notice.content", null, null);
        BLANK_NOTICE_COMMENT = messageSource.getMessage("blank.notice.comment", null, null);
        FORBIDDEN = messageSource.getMessage("forbidden", null, null);
        ERROR = messageSource.getMessage("error", null, null);
    }
}
