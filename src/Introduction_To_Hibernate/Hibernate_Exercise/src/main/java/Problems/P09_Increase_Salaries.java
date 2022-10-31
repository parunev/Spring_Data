package Problems;

import Queries.Queries;
import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class P09_Increase_Salaries { // Parunev
    public static void main(String[] args) {
        EntityManagerFactory factory =
                Persistence.createEntityManagerFactory("soft_uni");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        List<String> departmentNames = Arrays.asList( "Engineering", "Marketing",
                                                      "Tool Design", "Information Services");

        List<Department> departments = em.createQuery(Queries.P09F, Department.class)
                .setParameter("names", departmentNames)
                .getResultList();

        em.createQuery(Queries.P09S)
                .setParameter("departments", departments)
                .executeUpdate();

        em.createQuery(Queries.P09T, Employee.class)
                .setParameter("departmentNames", departmentNames)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s ($%.2f)%n"
                        , e.getFirstName(), e.getLastName(), e.getSalary()));

        em.getTransaction().commit();
        em.close();
    }
}
