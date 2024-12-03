package com.example.start.member;

import com.example.start.entity.Member;
import com.example.start.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MemberTest {

    @Mock
    private MemberRepository memberRepository;

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
