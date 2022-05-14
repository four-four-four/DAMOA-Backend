package com.fourfourfour.damoa.common.util;

import com.fourfourfour.damoa.common.auth.PrincipalDetails;
import com.fourfourfour.damoa.common.constant.ErrorMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    static public void verifyMember(Long memberSeq) {
        if (((PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getMember().getSeq().equals(memberSeq)) {
            return;
        }

        throw new AccessDeniedException(ErrorMessage.ERROR);
    }

    static public Long getMemberSeq() {
        return ((PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getMember().getSeq();
    }
}
