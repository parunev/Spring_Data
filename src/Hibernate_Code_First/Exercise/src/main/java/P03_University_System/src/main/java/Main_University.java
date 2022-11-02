import entities.Course;
import entities.Student;
import entities.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main_University {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("university_db");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Teacher teacher = new Teacher("Martin","Parunev","08889991", "parunev@softuni.org",
                BigDecimal.valueOf(50.00));

        Student student = new Student("Boriana","Paruneva","08888881", 5.5,12);

        Course course = new Course("Spring Data","Learn Spring", LocalDate.now(),
                LocalDate.of(2023,1,1), 21, teacher);

        em.persist(teacher);
        em.persist(student);
        em.persist(course);

        Teacher teacher1 = new Teacher("Testing","Testing","Testing", "Testing@softuni.org",
                BigDecimal.valueOf(50.00));

        Course course1 = new Course("Test","Test",LocalDate.now(), LocalDate.of(2025,1,1),21,teacher);

        Student student1 = new Student("Testtt","Testtt","Testtt", 5.5,12);

        em.persist(teacher1);
        em.persist(student1);
        em.persist(course1);


        em.getTransaction().commit();
        em.close();
    }
}
