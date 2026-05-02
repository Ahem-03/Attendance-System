package com.example.AttendanceSystem.Controller;

import com.example.AttendanceSystem.Entity.Students;
import com.example.AttendanceSystem.Entity.Teacher;
import com.example.AttendanceSystem.Service.StudentService;
import com.example.AttendanceSystem.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MyController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    @GetMapping("/")
    public String AddStudentPage(Model model){
        model.addAttribute("Data", new Students());
        return "LoginPage";
    }

    @PostMapping("/register")
    public  String LoginPage(@ModelAttribute("Data")
                                 Teacher teacher, Model model){
            boolean status = teacherService.saveTeacher(teacher);
        if (status) {
            model.addAttribute("success", "Successfully... ");
        }else {
            model.addAttribute("error", "Something went wrong... ");
        }
        return "redirect:/";
    }

    @GetMapping("/reportAttendance")
    public String openREport(Model model){
        model.addAttribute("student" , new Students());
        return "Report";
    }

    @PostMapping("/addForm")
    public String saveStudentData(@ModelAttribute("studentData")
                                  Students students, Model model){
        boolean status =  studentService.saveStudent(students);
        if (status) {
            model.addAttribute("succesMSG", "Successfully... ");
        }else {
            model.addAttribute("errorMSG", "Something went wrong... ");
        }
            return  "redirect:/";
    }

    @GetMapping("/addstudents")
    public  String addStudentPageFromMark(Model model){
        model.addAttribute("studentData", new Students());
        return "AddStudent";
    }

    @GetMapping("/markPage")
    public  String markAttendance(Model model){
        model.addAttribute("students" , studentService.getAllStudent());
        model.addAttribute("today" , java.time.LocalDate.now().toString());
        return "markAttendance";
    }
}
