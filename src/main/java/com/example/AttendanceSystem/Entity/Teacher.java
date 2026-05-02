package com.example.AttendanceSystem.Entity;
import jakarta.persistence.*;

@Entity
@Table
public class Teacher {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column
    private  long id;

    @Column
    private String name;

    @Column
    private   String email;

    @Column
    private String password;

    @Column
    private String department;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
