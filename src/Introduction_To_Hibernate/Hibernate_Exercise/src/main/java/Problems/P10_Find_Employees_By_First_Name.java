package Problems;

import Queries.Queries;
import entities.Employee;

import javax.persistence.Persistence;
import java.util.Scanner;

public class P10_Find_Employees_By_First_Name { // Parunev
    public static void main(String[] args) {

        String pattern = new Scanner(System.in).nextLine();

        Persistence.createEntityManagerFactory("soft_uni")
                .createEntityManager()
                .createQuery(Queries.P10, Employee.class)
                .setParameter("regex", pattern+"%")
                .getResultList()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }
}
