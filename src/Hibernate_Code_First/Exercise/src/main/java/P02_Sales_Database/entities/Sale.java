package P02_Sales_Database.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_location_id", referencedColumnName = "id")
    private StoreLocation storeLocation;

    @Column(nullable = false)
    private LocalDateTime date;

    private Sale(){}

    public Sale(Product product, Customer customer, StoreLocation storeLocation, LocalDateTime date) {
        this.product = product;
        this.customer = customer;
        this.storeLocation = storeLocation;
        this.date = date;
    }

    public long getId() {return id;}
    public void setId(int id) {this.id = id;}

    public Product getProduct() {return product;}
    public void setProduct(Product product) {this.product = product;}

    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}

    public StoreLocation getStoreLocation() {return storeLocation;}
    public void setStoreLocation(StoreLocation storeLocation) {this.storeLocation = storeLocation;}

    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) {this.date = date;}
}
