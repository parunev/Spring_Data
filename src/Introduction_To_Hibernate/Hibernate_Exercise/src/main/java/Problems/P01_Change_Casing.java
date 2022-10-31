package Problems;

import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class P01_Change_Casing { // Parunev
    public static void main(String[] args) {
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory("soft_uni");

            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();

            Query from_query = em.createQuery("FROM Town", Town.class);
            List<Town> resultList = from_query.getResultList();

            // change to upper case every single town name where character length is more than 5
            resultList.stream().filter(t -> t.getName().length() <= 5)
                    .forEach(t -> { t.setName(t.getName().toUpperCase());
                        em.persist(t);
                    });

            em.getTransaction().commit();
            em.close();
    }
}
