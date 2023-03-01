package com.jpa.jpashop.repository;

import com.jpa.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus;

}
