package P01_Gringotts_Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main_Gringotts {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gringotts_db");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        WizardDeposits wd = new WizardDeposits("Martin","Parunev","One of a kind geek", 23,
                "Harry Potter", 10, "Hogwarts",
                LocalDateTime.now(), BigDecimal.valueOf(10000.00), BigDecimal.valueOf(12), BigDecimal.valueOf(1500),
                LocalDateTime.of(2025, 1, 1, 0, 0),false);

        em.persist(wd);
        em.getTransaction().commit();
        em.close();
    }
}
