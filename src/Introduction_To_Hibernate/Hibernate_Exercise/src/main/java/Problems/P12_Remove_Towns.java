package Problems;

import Queries.Queries;
import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P12_Remove_Towns {
    public static void main(String[] args) {

        String input = new Scanner(System.in).nextLine();
        EntityManager em = Persistence
                .createEntityManagerFactory("soft_uni")
                .createEntityManager();

        em.getTransaction().begin();

        try {
            //first we get the addresses
            List<Address> addresses = em.
                    createQuery(Queries.P12, Address.class).
                    setParameter("townName", input)
                    .getResultList();

            //secondly we remove the addresses from employees
            addresses.forEach(a -> {
                a.getEmployees().forEach(e -> e.setAddress(null));
                em.remove(a);
            });

            //we remove the town
            Town town = em.createQuery(Queries.P12F, Town.class)
                    .setParameter("townName", input)
                    .getSingleResult();
            em.remove(town);

            em.getTransaction().commit();

            System.out.println(addresses.size() + (addresses.size() == 1 ? " address in " : " addresses in ") +
                    input + " deleted");

        } catch (Exception e) {
            em.getTransaction().rollback();
        }

        em.close();
    }
}
