package com.example.start.controller;

import com.example.start.DTO.MemberCreateForm;
import com.example.start.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<String> createMember(@Valid @RequestBody MemberCreateForm memberCreateForm) {
        memberService.createMember(memberCreateForm);

        return ResponseEntity.ok("Member created successfully");
    }

}
