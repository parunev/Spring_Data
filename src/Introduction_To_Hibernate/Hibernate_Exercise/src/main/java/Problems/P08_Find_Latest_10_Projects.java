package Problems;

import Queries.Queries;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;

public class P08_Find_Latest_10_Projects { // Parunev
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("soft_uni");

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        em.createQuery(Queries.P08, Project.class).setMaxResults(10).getResultList()
                .stream().sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.printf(
                        "Project name: %s%n" +
                        "        Project Description: %s%n" +
                        "        Project Start Date:%s%n" +
                        "        Project End Date: %s%n",
                        project.getName(), project.getDescription(), project.getStartDate(), project.getEndDate()));

        em.getTransaction().commit();
        em.close();
    }
}
