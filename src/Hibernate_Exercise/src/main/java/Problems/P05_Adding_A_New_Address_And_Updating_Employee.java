package Problems;

import Queries.Queries;
import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class P05_Adding_A_New_Address_And_Updating_Employee { // Parunev
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("soft_uni");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        Address address = new Address();
        address.setText("Vitoshka 15"); // hardcode
        em.persist(address);

        String lastName = new Scanner(System.in).nextLine();
        em.createQuery(Queries.P05).setParameter("last_name", lastName).setParameter("address",  address)
                        .executeUpdate();

        em.getTransaction().commit();
        em.close();
    }
}
