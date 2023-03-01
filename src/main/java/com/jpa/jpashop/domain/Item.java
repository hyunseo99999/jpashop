package com.jpa.jpashop.domain;

import com.jpa.jpashop.exception.NotEnorghStockExcpetion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    /**
     * addStock, removeStock
     * 객체지향 관점
     * 데이터 가지고 있는 곳에서 비지니스 로직을 가지고 있는게 좋음(관리하기 편하기 위해서)
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int divQuantity = this.stockQuantity - quantity;
        if (divQuantity < 1) {
            throw new NotEnorghStockExcpetion("need more stock");
        }
        this.stockQuantity = divQuantity;
    }
}
