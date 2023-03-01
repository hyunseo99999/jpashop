package com.jpa.jpashop.service;

import com.jpa.jpashop.domain.Item;
import com.jpa.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Long save(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

}
