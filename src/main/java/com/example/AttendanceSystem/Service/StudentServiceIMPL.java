package com.example.AttendanceSystem.Service;

import com.example.AttendanceSystem.Entity.Students;
import com.example.AttendanceSystem.Repositry.StudentRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceIMPL implements StudentService{

    @Autowired
    private StudentRepositry studentRepositry;

    @Override
    public boolean saveStudent(Students students) {
        try {
            studentRepositry.save(students);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
