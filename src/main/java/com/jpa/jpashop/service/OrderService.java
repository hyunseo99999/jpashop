package com.jpa.jpashop.service;

import com.jpa.jpashop.domain.*;
import com.jpa.jpashop.repository.ItemRepository;
import com.jpa.jpashop.repository.MemberRepository;
import com.jpa.jpashop.repository.OrderRepository;
import com.jpa.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        Member findMember = memberRepository.findOne(memberId);
        Item findItem = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(findMember.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice() , count);
        Order order = Order.createOrder(findMember, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancel(Long id) {
        Order order = orderRepository.findOne(id);
        // 주문 취소
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

    public List<Order> findByOrderWithMemberDelivery() {
        return orderRepository.findByOrderWithMemberDelivery();
    }

    public List<Order> findByOrderWithItem() {
        return orderRepository.findByOrderWithItem();
    }
}
