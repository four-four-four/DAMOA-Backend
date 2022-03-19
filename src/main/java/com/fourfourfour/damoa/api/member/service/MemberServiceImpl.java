package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.dto.req.ReqRegisterMemberDto;
import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;
import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.enums.Gender;
import com.fourfourfour.damoa.api.member.enums.Role;
import com.fourfourfour.damoa.api.member.repository.MemberRepository;
import com.fourfourfour.damoa.common.message.ErrorMessage;
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
    public Member register(ReqRegisterMemberDto reqRegisterMemberDto) {
        String gender = reqRegisterMemberDto.getGender();

        Member newMember = Member.builder()
                .email(reqRegisterMemberDto.getEmail())
                .password(passwordEncoder.encode(reqRegisterMemberDto.getPassword()))
                .nickname(reqRegisterMemberDto.getNickname())
                .gender(gender == null ? null : Gender.valueOf(gender.toUpperCase()))
                .birthDate(reqRegisterMemberDto.getBirthDate())
                .job(reqRegisterMemberDto.getJob())
                .serviceTerm(reqRegisterMemberDto.isServiceTerm())
                .privacyTerm(reqRegisterMemberDto.isPrivacyTerm())
                .role(Role.MEMBER)
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
    public ResMemberDto getResMemberDtoByEmail(String email) {
        return memberRepository.findResMemberDtoByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElse(null);
    }

    @Transactional
    @Override
    public void deleteAll() {
        memberRepository.deleteAll();
    }
}
