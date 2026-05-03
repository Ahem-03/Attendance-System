package com.example.AttendanceSystem.Repositry;

import com.example.AttendanceSystem.Entity.Students;
import com.example.AttendanceSystem.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepositry  extends JpaRepository<Students , Long> {
    Students findByEmailAndPassword(String email, String password);
}
