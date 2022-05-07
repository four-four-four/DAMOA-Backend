package com.fourfourfour.damoa.common.util;

import com.fourfourfour.damoa.common.auth.PrincipalDetails;
import com.fourfourfour.damoa.common.constant.ErrorMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {

    public void verifyMember(Long memberSeq) {
        if (((PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getMember().getSeq().equals(memberSeq)) {
            return;
        }

        throw new AccessDeniedException(ErrorMessage.ERROR);
    }

    public Long getMemberSeq() {
        return ((PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getMember().getSeq();
    }
}
