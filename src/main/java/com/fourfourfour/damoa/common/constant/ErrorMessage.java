package com.fourfourfour.damoa.common.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessage {

    static public String AUTHENTICATION_MEMBER;

    static public String PATTERN;

    static public String PATTERN_MEMBER_EMAIL;

    static public String PATTERN_MEMBER_PASSWORD;

    static public String PATTERN_MEMBER_NICKNAME;

    static public String PATTERN_MEMBER_TERM;

    static public String NULL_MEMBER;

    static public String NULL_MEMBER_EMAIL;

    static public String NULL_NOTICE;

    static public String BLANK_NOTICE_TITLE;

    static public String BLANK_NOTICE_CONTENT;

    static public String BLANK_NOTICE_COMMENT;

    static public String FORBIDDEN;

    static public String ERROR;
    
    static public String BANNED_KEYWORD;

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
        BANNED_KEYWORD = messageSource.getMessage("banned.keyword", null, null);
    }
}
