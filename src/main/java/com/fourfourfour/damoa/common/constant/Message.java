package com.fourfourfour.damoa.common.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class Message {

    static public String REGISTER_MEMBER;

    static public String LOGIN;

    static public String DUPLICATE_MEMBER_EMAIL;

    static public String DUPLICATE_MEMBER_NICKNAME;

    static public String USABLE_MEMBER_EMAIL;

    static public String USABLE_MEMBER_NICKNAME;

    static public String REGISTER_NOTICE;

    @Autowired
    public Message(MessageSource messageSource) {
        REGISTER_MEMBER = messageSource.getMessage("register.member", null, null);
        LOGIN = messageSource.getMessage("login", null, null);
        DUPLICATE_MEMBER_EMAIL = messageSource.getMessage("duplicate.member.email", null, null);
        DUPLICATE_MEMBER_NICKNAME = messageSource.getMessage("duplicate.member.nickname", null, null);
        USABLE_MEMBER_EMAIL = messageSource.getMessage("usable.member.email", null, null);
        USABLE_MEMBER_NICKNAME = messageSource.getMessage("usable.member.nickname", null, null);
        REGISTER_NOTICE = messageSource.getMessage("register.notice", null, null);
    }
}
