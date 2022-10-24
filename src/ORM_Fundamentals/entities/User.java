package ORM_Fundamentals.entities;

import ORM_Fundamentals.annotations.Column;
import ORM_Fundamentals.annotations.Entity;
import ORM_Fundamentals.annotations.Id;

import java.time.LocalDate;

@Entity(name = "users")
public class User {

    // INT PRIMARY KEY AUTO_INCREMENT
    @Id
    @Column(name = "id")
    private long id;

    // VARCHAR(30) NOT NULL
    @Column(name = "username")
    private String username;

    // INT NOT NULL
    @Column(name = "age")
    private int age;

    //registration should be set to DATE (SQL Language) but it works just fine with varchar too :)) VARCHAR(100) NOT NULL
    @Column(name = "registration")
    private LocalDate registration;

    public User(String username, int age, LocalDate registration){
        this.username = username;
        this.age = age;
        this.registration = registration;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }
    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }
    public LocalDate getRegistration() {
        return registration;
    }
}
