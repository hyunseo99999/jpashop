package com.jpa.jpashop.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class CreateMemberResponse {
    private Long id;
    private String name;
}
