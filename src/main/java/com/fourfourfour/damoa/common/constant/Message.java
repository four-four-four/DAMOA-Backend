package com.fourfourfour.damoa.common.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class Message {

    public static String REGISTER_MEMBER;

    public static String LOGIN;

    public static String DUPLICATE_MEMBER_EMAIL;

    public static String DUPLICATE_MEMBER_NICKNAME;

    public static String USABLE_MEMBER_EMAIL;

    public static String USABLE_MEMBER_NICKNAME;

    public static String REGISTER_NOTICE;

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
