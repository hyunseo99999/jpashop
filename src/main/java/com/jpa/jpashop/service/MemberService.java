package com.jpa.jpashop.service;

import com.jpa.jpashop.domain.Member;
import com.jpa.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicationMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicationMember(Member member) {
        List<Member> findByName = memberRepository.findByName(member.getName());
        if (!findByName.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
