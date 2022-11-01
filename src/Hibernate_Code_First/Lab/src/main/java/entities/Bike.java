package entities;

import jakarta.persistence.Entity;

@Entity
public class Bike extends Vehicle {
    private static final String BIKE_TYPE = "BIKE";

    public Bike(){
        super(BIKE_TYPE);
    }

    public Bike(String model, double price){
        this();

        this.model = model;
        this.price = price;
    }
}
