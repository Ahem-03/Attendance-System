package com.example.AttendanceSystem.Service;

import com.example.AttendanceSystem.Entity.Teacher;

public interface TeacherService {
    public boolean saveTeacher(Teacher teacher);
    public  Teacher validTeacher(String email, String password);
}
