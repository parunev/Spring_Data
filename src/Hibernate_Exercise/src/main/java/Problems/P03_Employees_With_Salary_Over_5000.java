package Problems;

import Queries.Queries;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P03_Employees_With_Salary_Over_5000 { // Parunev
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("soft_uni");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        List<String> employeeList = em.createQuery(Queries.PO3).getResultList();

        System.out.println(String.join("\n", employeeList));

        em.getTransaction().commit();
        em.close();
    }
}
