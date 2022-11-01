package P02_Sales_Database;

import P02_Sales_Database.entities.Customer;
import P02_Sales_Database.entities.Product;
import P02_Sales_Database.entities.Sale;
import P02_Sales_Database.entities.StoreLocation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main_Sales {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sales_db");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //For more precise view we need to add more stores, products and customers
        StoreLocation storeLocation = new StoreLocation("Sea Food");
        Product product = new Product("Star Fish",5, BigDecimal.valueOf(10.50));
        Customer customer = new Customer("Martin","parunev@gmail.com","47730023###");

        em.persist(storeLocation);
        em.persist(product);
        em.persist(customer);

        Sale sale = new Sale(product, customer, storeLocation, LocalDateTime.now());

        em.persist(sale);

        em.getTransaction().commit();
        em.close();
    }
}
