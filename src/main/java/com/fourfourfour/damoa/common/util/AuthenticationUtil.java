package com.fourfourfour.damoa.common.util;

import com.fourfourfour.damoa.common.auth.PrincipalDetails;
import com.fourfourfour.damoa.common.constant.ErrorMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {

    public void verifyMemberSeq(Authentication authentication, Long memberSeq) {
        if (((PrincipalDetails) authentication.getDetails()).getMember().getSeq().equals(memberSeq)) {
            return;
        }

        throw new AccessDeniedException(ErrorMessage.ERROR);
    }

    public Long getMemberSeq(Authentication authentication) {
        return ((PrincipalDetails) authentication.getDetails()).getMember().getSeq();
    }
}
