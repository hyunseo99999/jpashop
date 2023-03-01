package com.jpa.jpashop.service;

import com.jpa.jpashop.domain.Member;
import com.jpa.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    @Rollback(value = false)
    void save() {
        Member member = new Member();
        member.setName("kim");

        Long memberId = memberService.join(member);

        Member findMember = memberService.findOne(memberId);

        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    void duplicationMember() {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);

        // then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }
}