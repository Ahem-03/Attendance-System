package com.example.AttendanceSystem.Service;

import com.example.AttendanceSystem.Entity.Students;

import java.util.List;

public interface StudentService {
    public  boolean saveStudent(Students students);
    public List<Students> getAllStudent();
}
