package com.jpa.jpashop.api.controller;

import com.jpa.jpashop.domain.Address;
import com.jpa.jpashop.domain.Order;
import com.jpa.jpashop.domain.OrderStatus;
import com.jpa.jpashop.repository.OrderSearch;
import com.jpa.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApiSimpleOrderController {

    private final OrderService orderService;

    @GetMapping("/api/v2/simple-orders")
    public List<SimpeOrderDto> orderV2() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        return  orders.stream().map(SimpeOrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpeOrderDto> orderV3() {
        List<Order> orders = orderService.findByOrderWithMemberDelivery();
        return  orders.stream().map(SimpeOrderDto::new).collect(Collectors.toList());
    }

    @Data
    static class SimpeOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpeOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}
