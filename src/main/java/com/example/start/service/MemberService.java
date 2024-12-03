package com.example.start.service;

import com.example.start.DTO.MemberCreateForm;
import com.example.start.entity.Member;
import com.example.start.exception.CustomException;
import com.example.start.exception.CustomResponseException;
import com.example.start.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(@Valid MemberCreateForm memberCreateForm) {
        if(memberRepository.existsByEmail(memberCreateForm.getEmail())) {
            throw new CustomException(CustomResponseException.ALREADY_EXISTS_EMAIL);
        }

        return memberRepository.save(
                Member.builder()
                        .name(memberCreateForm.getName())
                        .email(memberCreateForm.getEmail())
                        .build()
        );
    }
}
