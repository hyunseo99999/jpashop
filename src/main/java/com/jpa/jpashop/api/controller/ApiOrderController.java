package com.jpa.jpashop.api.controller;

import com.jpa.jpashop.domain.*;
import com.jpa.jpashop.repository.OrderSearch;
import com.jpa.jpashop.repository.order.query.OrderQueryDto;
import com.jpa.jpashop.repository.order.query.OrderQueryRepository;
import com.jpa.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApiOrderController {
    private final OrderService orderService;
    private final OrderQueryRepository orderQueryRepository;
    @GetMapping("/api/v1/orders")
    public List<OrderDto> orderV1() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2() {
        List<Order> orders = orderService.findByOrderWithItem();
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> orderV3() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> orderV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems = new ArrayList<>();

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }

}
