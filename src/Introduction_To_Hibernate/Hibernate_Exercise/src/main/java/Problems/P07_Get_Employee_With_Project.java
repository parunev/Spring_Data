package Problems;

import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.Scanner;

public class P07_Get_Employee_With_Project { // Parunev
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("soft_uni");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        int id = new Scanner(System.in).nextInt();
        Employee employee = em.find(Employee.class, id); // we don't need to create query

        //not the best looking but works just fine
        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        employee.getProjects().stream().sorted(Comparator.comparing(Project::getName))
                        .forEach(project -> System.out.println("      " + project.getName()));

        em.getTransaction().commit();
        em.close();
    }
}
