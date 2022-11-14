package com.example.spring_game_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Table
@Entity(name = "users")
public class User extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column
    private boolean isAdmin;

    @ManyToMany(targetEntity = Game.class, fetch = FetchType.EAGER)
    private Set<Game> games;

    @OneToMany(mappedBy = "buyer", targetEntity = Order.class, fetch = FetchType.EAGER)
    private Set<Order> orders;

    @Transient
    private Set<Game> shoppingCart;

    public User(){
        this.games = new HashSet<>();
        this.orders = new HashSet<>();
        this.shoppingCart = new HashSet<>();
    }

    public void addToShoppingCart(Game game) {
        this.shoppingCart.add(game);
    }

    public void removeFromShoppingCart(Game game) {
        this.shoppingCart.remove(game);
    }

    public void clearShoppingCart() {
        this.shoppingCart.clear();
    }

}
