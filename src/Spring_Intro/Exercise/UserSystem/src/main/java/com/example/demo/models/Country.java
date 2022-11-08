package com.example.demo.models;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "country", targetEntity = Town.class)
    private Set<Town> towns;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public Country(String name, Set<Town> towns) {
        this.name = name;
        this.towns = towns;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Town> getTowns() {
        return towns;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }
}
