package com.fourfourfour.damoa.common.auth;

import com.fourfourfour.damoa.api.member.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class PrincipalDetails implements UserDetails {

    private Member member;

    List<GrantedAuthority> roles = new ArrayList<>();

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public void setAuthorities(List<GrantedAuthority> roles) {
        this.roles = roles;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    /**
     * 만료된 계정인가?
     *
     * @return 만료가 아님
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 잠긴 계정인가?
     * 
     * @return 잠기지 않음
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 너무 오래된 계정인가?
     * 
     * @return 오랜된 계정이 아님
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 활성화된 계정인가?
     * 
     * @return 활성화된 계정임
     */
    @Override
    public boolean isEnabled() {
        /**
         * 이 메서드로 휴면 계정 전환을 할 수 있다.
         * 사용한다면, UserDto에 로그인 시간을 기록하는 프로퍼티를 추가하고
         * 현재 시간과 비교하여 1년이 초과하면 false를 리턴하면 된다.
         */

        return true;
    }
}
