package com.example.AttendanceSystem.Service;

import com.example.AttendanceSystem.Entity.Teacher;
import com.example.AttendanceSystem.Repositry.TeacherRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceIMPL implements TeacherService {

    @Autowired
    private TeacherRepositry teacherRepositry;

    @Override
    public boolean saveTeacher(Teacher teacher) {
        try {
            teacherRepositry.save(teacher);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
