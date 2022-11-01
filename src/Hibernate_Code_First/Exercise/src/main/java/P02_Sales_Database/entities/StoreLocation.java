package P02_Sales_Database.entities;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "store_location")
public class StoreLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @OneToMany(mappedBy = "storeLocation")
    private Set<Sale> sales;

    public StoreLocation(){}

    public StoreLocation(String locationName) {
        this.locationName = locationName;
        this.sales = new HashSet<>();
    }

    public void addSale(Sale sale){
        this.sales.add(sale);
    }

    public void removeSale(Sale sale){
        this.sales.remove(sale);
    }

    public Set<Sale> getSale(){
        return Collections.unmodifiableSet(sales);
    }

    public long getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getLocationName() {return locationName;}
    public void setLocationName(String locationName) {this.locationName = locationName;}
}
