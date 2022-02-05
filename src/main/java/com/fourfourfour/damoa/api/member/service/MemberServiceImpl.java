package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.dto.req.ReqRegisterMemberDto;
import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;
import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(ReqRegisterMemberDto reqRegisterMemberDto) {
        LocalDate birthDate = reqRegisterMemberDto.getBirthDate();
        return memberRepository.save(Member.builder()
                .email(reqRegisterMemberDto.getEmail())
                .password(passwordEncoder.encode(reqRegisterMemberDto.getPassword()))
                .nickname(reqRegisterMemberDto.getNickname())
                .gender(reqRegisterMemberDto.getGender())
                .birthDate(birthDate == null ? null : LocalDateTime.of(birthDate, LocalTime.now()))
                .job(reqRegisterMemberDto.getJob())
                .serviceTerm(reqRegisterMemberDto.isServiceTerm())
                .privacyTerm(reqRegisterMemberDto.isPrivacyTerm())
                .role("ROLE_member")
                .build());
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
        return memberRepository.findResMemberDtoByEmail(email);
    }

}
