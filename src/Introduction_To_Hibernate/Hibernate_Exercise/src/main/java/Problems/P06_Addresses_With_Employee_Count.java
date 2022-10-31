package Problems;

import Queries.Queries;
import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class P06_Addresses_With_Employee_Count { // Parunev
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("soft_uni");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        em.createQuery(Queries.P06, Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a -> System.out.printf( "%s, %s - %d employees%n"
                        , a.getText(), a.getTown().getName(), a.getEmployees().size()));

        em.getTransaction().commit();
        em.close();
    }
}
