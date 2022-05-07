package com.fourfourfour.damoa.domain.member.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.member.service.dto.MemberDto;
import com.fourfourfour.damoa.domain.member.service.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member register(MemberDto.RegisterDto registerDto) {
        Member newMember = Member.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .nickname(registerDto.getNickname())
                .gender(registerDto.getGender())
                .birthDate(registerDto.getBirthDate())
                .job(registerDto.getJob())
                .serviceTerm(registerDto.isServiceTerm())
                .privacyTerm(registerDto.isPrivacyTerm())
                .role(Member.Role.MEMBER)
                .build();

        return memberRepository.save(newMember);
    }

    @Override
    public boolean isEmailDuplication(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean isNicknameDuplication(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public boolean login(String email, String password) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        return passwordEncoder.matches(password, findMember.getPassword());
    }

    @Override
    public MemberResponseDto.LoginInfo getLoginDtoByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        return MemberResponseDto.LoginInfo.builder()
                .seq(findMember.getSeq())
                .email(findMember.getEmail())
                .nickname(findMember.getNickname())
                .role(findMember.getRole())
                .build();
    }

    @Transactional
    @Override
    public void deleteAll() {
        memberRepository.deleteAll();
    }
}
