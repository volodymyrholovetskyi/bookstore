package com.volodymyr.bookaro.order.domain;

import com.volodymyr.bookaro.jpa.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipient extends BaseEntity {

    private String name;
    private String phone;
    private String street;
    private String city;
    private String zipCod;
    private String email;

}
