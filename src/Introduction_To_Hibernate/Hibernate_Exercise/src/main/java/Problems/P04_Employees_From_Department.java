package Problems;

import Queries.Queries;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P04_Employees_From_Department { // Parunev
    private static final String DEPARTMENTS = "Research and Development";

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("soft_uni");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        List<Employee> employeeList = em.createQuery(Queries.P04, Employee.class)
                .setParameter("departmentName", DEPARTMENTS)
                .getResultList();

        employeeList.forEach(e -> System.out.printf("%s %s from %s - %.2f%n",
                e.getFirstName(), e.getLastName(), DEPARTMENTS, e.getSalary()));

        em.getTransaction().commit();
        em.close();
    }
}
