package Problems;

import Queries.Queries;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Scanner;

public class P02_Contains_Employee { // Parunev
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("soft_uni");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        String[] nameInput = new Scanner(System.in).nextLine().split("\\s+");
        String firstName = nameInput[0]; String lastName = nameInput[1];

        String output = checkIfDataBaseContainsEmployee(firstName, lastName, em);
        System.out.println(output);

        em.getTransaction().commit();
        em.close();
    }


    // long is a primitive type, while Long is a Java class (and so it will inherit Object).
    // long must be assigned with a valid number, while Long can be null.
    // long instances can't use the benefits of OO, while instances of Long are real Java objects.
    private static String checkIfDataBaseContainsEmployee(String firstName, String lastName, EntityManager em) {
        Long record =
                em.createQuery(Queries.P02, Long.class)
                        .setParameter("first_name", firstName)
                        .setParameter("last_name", lastName)
                        .getSingleResult();

        if (record == 0){
            return "No";
        }else{
            return "Yes";
        }
    }

}
