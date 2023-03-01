package com.jpa.jpashop.repository;

import com.jpa.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ItemRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i").getResultList();
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

}
