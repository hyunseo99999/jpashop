package com.jpa.jpashop.repository;

import com.jpa.jpashop.api.dto.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    /*public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new
                jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto(o.id, m.name,
                        o.orderDate, o.status, d.address)" +
    }*/
}
