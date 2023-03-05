package com.jpa.jpashop.api.controller;

import com.jpa.jpashop.api.dto.MemberDto;
import com.jpa.jpashop.api.request.CreateMemberRequest;
import com.jpa.jpashop.api.request.UpdateMemberRequest;
import com.jpa.jpashop.api.response.CreateMemberResponse;
import com.jpa.jpashop.api.response.UpdateMemberResponse;
import com.jpa.jpashop.domain.Member;
import com.jpa.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApiMemberController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public Result getMembers() {
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream().map(item -> new MemberDto(item.getName())).collect(Collectors.toList());
        return new Result(collect);
    }


    @PostMapping("/api/v1/members")
    public CreateMemberResponse member(@RequestBody @Valid CreateMemberRequest createMemberRequest) {
        Member member = new Member();
        member.setName(createMemberRequest.getName());

        memberService.join(member);

        Member findOne = memberService.findOne(member.getId());
        return new CreateMemberResponse(findOne.getId(), findOne.getName());
    }

    @PutMapping("/api/v1/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


}
