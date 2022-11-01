import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import relations.Car;
import relations.PlateNumber;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vehicles");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //SINGLE TABLE
/*
        Vehicle bike = new Bike("Drag", 400);
        Vehicle car = new Car("Mercedes-Benz", "Diesel", 4,35000);
        Vehicle plane = new Plane("Boing-747", "Diesel", 300, 100000);
        Vehicle truck = new Truck("MAN", "Diesel", 12,56000);

        em.persist(bike);
        em.persist(car);
        em.persist(plane);
        em.persist(truck);
*/

        // TABLE RELATIONS ONE-TO-ONE

        PlateNumber plateNumber = new PlateNumber("0525");
        Car car = new Car(plateNumber);
        em.persist(plateNumber);
        em.persist(car);

        em.getTransaction().commit();
        em.close();
    }
}
