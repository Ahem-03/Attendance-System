package com.example.AttendanceSystem.Repositry;

import com.example.AttendanceSystem.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepositry extends JpaRepository<Teacher , Long> {

}
