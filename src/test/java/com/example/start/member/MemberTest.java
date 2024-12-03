package com.example.start.member;

import com.example.start.DTO.MemberCreateForm;
import com.example.start.controller.MemberController;
import com.example.start.entity.Member;
import com.example.start.exception.GlobalControllerAdvice;
import com.example.start.repository.MemberRepository;
import com.example.start.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MemberTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @InjectMocks
    private GlobalControllerAdvice globalControllerAdvice;

    private MockMvc mockMvc;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();

        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(globalControllerAdvice)
                .build();
    }

    @Test
    public void testInvalidEmail() throws Exception {
        MemberCreateForm form = new MemberCreateForm();
        ReflectionTestUtils.setField(form, "name", "Manuk Han");
        ReflectionTestUtils.setField(form, "email", "aksdnr507@gmail.com");

        Set<ConstraintViolation<MemberCreateForm>> violations = validator.validate(form);

        System.out.println("Violations: ");
        violations.forEach(violation -> {
            System.out.println("Property: " + violation.getPropertyPath());
            System.out.println("Message: " + violation.getMessage());
        });

        assertThat(violations).isNotEmpty();
        assertThat(violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("email") &&
                        violation.getMessage().equals("올바른 이메일 형식을 입력해주세요.")))
                .isTrue();
    }

    @Test
    public void testCreateMember() {
        Mockito.when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            member.setId(1L);
            return member;
        });

        Member member = Member.builder()
                .name("Manuk Han")
                .email("aksdnr507@gmail.com")
                .build();

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo("Manuk Han");
        assertThat(savedMember.getEmail()).isEqualTo("aksdnr507@gmail.com");
    }

    @Test
    public void createMemberWithMemberCreateForm() {
        Mockito.when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            member.setId(1L);
            return member;
        });

        MemberCreateForm form = new MemberCreateForm();
        ReflectionTestUtils.setField(form, "name", "Manuk Han");
        ReflectionTestUtils.setField(form, "email", "aksdnr507@gmail.com");

        Member member = Member.builder()
                .name(form.getName())
                .email(form.getEmail())
                .build();

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo("Manuk Han");
        assertThat(savedMember.getEmail()).isEqualTo("aksdnr507@gmail.com");
    }

    @Test
    public void testCreateMemberService() {
        MemberCreateForm form = MemberCreateForm.builder()
                .name("Manuk Han")
                .email("aksdnr507@gmail.com")
                .build();

        Mockito.when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            member.setId(1L);
            return member;
        });

        Member createdMember = memberService.createMember(form);

        assertThat(createdMember.getId()).isNotNull();
        assertThat(createdMember.getName()).isEqualTo("Manuk Han");
        assertThat(createdMember.getEmail()).isEqualTo("aksdnr507@gmail.com");
    }

    @Test
    public void testCreateMemberAPI_ValidData() throws Exception {
        String requestBody = """
        {
            "name": "Manuk Han",
            "email": "aksdnr507@gmail.com"
        }
        """;

        mockMvc.perform(post("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Member created successfully"));
    }


    @Test
    public void testGetMember() {
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(
                Member.builder()
                        .id(1L)
                        .name("Manuk Han")
                        .email("aksdnr507@gmail.com")
                        .build()
        ));

        Member savedMember = memberRepository.findById(1L).orElse(null);

        assert savedMember != null;
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo("Manuk Han");
        assertThat(savedMember.getEmail()).isEqualTo("aksdnr507@gmail.com");
    }


}
