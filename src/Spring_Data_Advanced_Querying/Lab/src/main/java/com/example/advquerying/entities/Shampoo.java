package com.example.advquerying.entities;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "shampoos")
public class Shampoo extends BaseEntity {

    private String brand;
    private BigDecimal price;
    private Size size;
    private Label label;
    private Set<Ingredient> ingredients;

    public Shampoo() {
    }

    @Column(name = "brand")
    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "size")
    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @ManyToOne(optional = true, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "label", referencedColumnName = "id")
    public Label getLabel() {
        return this.label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    //fetch-a връща join-овете, inner-таблиците и реално всичко свързано със заявката
    //Set пази уникални елементи, ще се погрижи да не сме добавяли едно и също нещо два пъти
    //Set постига уникалност т.е. само уникални елементи
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "shampoos_ingredients",
            joinColumns = @JoinColumn(name = "shampoo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    public Set<Ingredient> getIngredients() {return this.ingredients;}
    //Вместо Set може да е всякаква линейна колекция

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Shampoo{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                ", size=" + size +
                ", label=" + label +
                ", ingredients=" + ingredients +
                '}';
    }
}
