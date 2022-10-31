package entities;

import entities.Vehicle;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;


@Entity
public class Truck extends Vehicle {
    private static final String TRUCK_TYPE = "TRUCK";

    @Basic
    private double capacity;

    public Truck(){
        super(TRUCK_TYPE);
    }

    public Truck(String model, String fuelType, double capacity, double price){
        this();

        this.price = price;
        this.model = model;
        this.fuelType = fuelType;
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
}
