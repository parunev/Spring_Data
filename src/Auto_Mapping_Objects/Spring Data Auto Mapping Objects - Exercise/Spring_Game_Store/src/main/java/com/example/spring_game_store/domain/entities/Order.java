package com.example.spring_game_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "orders")
public class Order extends BaseEntity{

    @ManyToOne(targetEntity = User.class)
    private User buyer;

    @ManyToMany(targetEntity = Game.class)
    private Set<Game> items;

    @Transient
    private BigDecimal itemsTotalPrice;

    public Order(User currentUser, Set<Game> shoppingCart) {
        this.buyer = currentUser;
        this.items = shoppingCart;
    }
}
