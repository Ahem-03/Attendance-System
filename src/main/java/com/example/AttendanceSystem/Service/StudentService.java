package com.example.AttendanceSystem.Service;

import com.example.AttendanceSystem.Entity.Students;
import com.example.AttendanceSystem.Entity.Teacher;

import java.util.List;

public interface StudentService {
    public  boolean saveStudent(Students students);
    public List<Students> getAllStudent();

    Students validStudent(String email, String password);
}
