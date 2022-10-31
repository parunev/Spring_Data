package Problems;

import Queries.Queries;

import javax.persistence.Persistence;

public class P11_Employees_Maximum_Salaries {
    public static void main(String[] args) {
        Persistence
                .createEntityManagerFactory("soft_uni")
                .createEntityManager()
                .createQuery(Queries.P11, Object[].class)
                .getResultList()
                .forEach(d -> System.out.println(d[0] + " " + d[1]));
    }
}
