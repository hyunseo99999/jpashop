package com.jpa.jpashop.service;

import com.jpa.jpashop.domain.*;
import com.jpa.jpashop.domain.item.Book;
import com.jpa.jpashop.exception.NotEnorghStockExcpetion;
import com.jpa.jpashop.repository.ItemRepository;
import com.jpa.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문")
    void save() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);
        Assertions.assertThat(OrderStatus.ORDER).isEqualTo(order.getStatus()); // 주문 상태
        Assertions.assertThat(1).isEqualTo(order.getOrderItems().size()); // 주문 갯수
        Assertions.assertThat(10000 * orderCount).isEqualTo(order.getTotalPrice()); // 주문 가격
        Assertions.assertThat(8).isEqualTo(book.getStockQuantity()); // 수량
    }

    @Test
    @DisplayName("상품주문 재고 수량 초과")
    void stockQuantityMax() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(0);
        em.persist(book);

        int orderCount = 2;
        NotEnorghStockExcpetion thrown = assertThrows(NotEnorghStockExcpetion.class, () -> orderService.order(member.getId(), book.getId(), orderCount));
        assertEquals("need more stock", thrown.getMessage());
    }

    @Test
    @DisplayName("주문 취소")
    void orderCancel() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);

        Book book = new Book();
        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancel(orderId);

        Order order = orderRepository.findOne(orderId);

        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(order.getStatus()); // 주문 상태 취소
        Assertions.assertThat(10).isEqualTo(book.getStockQuantity()); // 주문이 취소된 상품은 그만큼 재고가 증가해야 한다.
    }




}