
import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

    /*     FIRST TEST - INSERT

         Student newOne = new Student();
         newOne.setName("Parunev");
         session.persist(newOne);

    /*    SECOND TEST - RETRIEVE DATE

        Student student = session.get(Student.class, 2);
        System.out.println(student.getId() + " " + student.getName());

    /*    THIRD TEST - RETRIEVE DATA BY QUERY

        Student new1 = new Student(); new1.setName("Teodor"); session.persist(new1);
        Student new2 = new Student(); new2.setName("Teodor"); session.persist(new2);
        Student new3 = new Student(); new3.setName("Teodor"); session.persist(new3);
        Student new4 = new Student(); new4.setName("Teodor"); session.persist(new4);

        List<Student> students = session
                      .createQuery("FROM Student as s WHERE s.name = 'Teodor'", Student.class)
                        .list();

        for (Student s : students) {
            System.out.println(s.getId() + " " + s.getName());
        }
     */

        session.getTransaction().commit();
        session.close();
    }
}
