package com.example.demo.models;

import com.example.demo.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 30)
    private String username;

    @Column(nullable = false)
    @Password(message = "invalid format for password", maxLength = 10, containsDigit = true)
    private String password;

    @Column(nullable = false, unique = true)
    @Email(message = "invalid format for email")
    private String email;

    @Column(name = "registered_on")
    private LocalDateTime registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDateTime lastTimeLoggedIn;

    private byte age;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Transient
    private String fullName;

    @ManyToMany
    private Set<User> friends;

    public User() {
    }

    public User(String username, String password, String email, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        setFullName();
        this.friends = new HashSet<>();
    }

    public User(String username, String password, String email, LocalDateTime registeredOn, LocalDateTime lastTimeLoggedIn, byte age, boolean isDeleted, String firstName, String lastName) {
        this(username, password, email, firstName, lastName);
        this.registeredOn = registeredOn;
        this.lastTimeLoggedIn = lastTimeLoggedIn;
        this.age = age;
        this.isDeleted = isDeleted;
    }

    public User(String username, String password, String email, LocalDateTime registeredOn, LocalDateTime lastTimeLoggedIn, byte age, boolean isDeleted, String firstName, String lastName, Set<User> friends) {
       this(username, password, email, registeredOn, lastTimeLoggedIn, age, isDeleted, firstName, lastName);
        this.friends = friends;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        PassConstraintValidator.isValid(password);
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        EmailConstraintValidator.isValid(email);
        this.email = email;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName() {
        this.fullName = firstName + " " + lastName;
    }

    public String getFullName() {
        setFullName();
        return fullName;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
}
