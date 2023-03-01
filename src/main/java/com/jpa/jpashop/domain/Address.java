package com.jpa.jpashop.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Getter
@Embeddable
public class Address {

    private String city;

    private String street;

    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
