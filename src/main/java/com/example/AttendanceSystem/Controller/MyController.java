package com.example.AttendanceSystem.Controller;

import com.example.AttendanceSystem.Entity.Attendance;
import com.example.AttendanceSystem.Entity.Students;
import com.example.AttendanceSystem.Entity.Teacher;
import com.example.AttendanceSystem.Service.AttendanceService;
import com.example.AttendanceSystem.Service.StudentService;
import com.example.AttendanceSystem.Service.TeacherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MyController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/")
    public String AddStudentPage(Model model){
        model.addAttribute("Data", new Students());
        return "LoginPage";
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

    //============Logic for marking the attendance===============
    @PostMapping("/MarkAttendance")
    public String SubmitAttendace(@RequestParam("date") String date,
                                  @RequestParam(value = "presentIds", required = false)
                                  List<Long> presentIds, RedirectAttributes redirectAttributes){
        LocalDate localDate = LocalDate.parse(date);
        if (attendanceService.isAlreadyMarked(localDate)) {
            redirectAttributes.addFlashAttribute("alreadyMarked", "true");
            return "redirect:/markPage";
        }
        List<Students> allStudents = studentService.getAllStudent();
        attendanceService.markAttendance(localDate, presentIds, allStudents);

        redirectAttributes.addFlashAttribute("success", "Attendance marked successfully!");
        return "redirect:/markPage";
    }
    //=================register the new teacher record ==================================

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
    //===========for opening the report page========================

    @GetMapping("/reportAttendance")
    public String openReport(Model model){
        model.addAttribute("students", studentService.getAllStudent());
        return "Report";
    }
// =================this is for register the new student record =====================

    @PostMapping("/addForm")
    public String saveStudentData(@ModelAttribute("studentData")
                                  Students students, HttpSession session, RedirectAttributes redirectAttributes){
        boolean status =  studentService.saveStudent(students);
        if (status) {
            redirectAttributes.addFlashAttribute("succesMSG", "Successfully... ");
        }else {
            redirectAttributes.addFlashAttribute("errorMSG", "Something went wrong... ");
        }
        return  "redirect:/addstudents";
    }
    //===========Login for teacher=====================

    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              @RequestParam("role") String role,
                              HttpSession session, Model model) {
        if (role.equals("teacher")) {
            // Teacher login
            Teacher found = teacherService.validTeacher(email, password);
            if (found != null) {
                session.setAttribute("LoggedIn", found);
                session.setAttribute("role", "TEACHER");
                session.setAttribute("userName", found.getName());
                return "redirect:/markPage";
            }
        } else if (role.equals("student")) {
            // Student login
            Students found2 = studentService.validStudent(email, password);
            if (found2 != null) {
                session.setAttribute("LoggedIn", found2);
                session.setAttribute("role", "STUDENT");
                session.setAttribute("userName", found2.getName());
                return "redirect:/studentDashboard";
            }
        }
        model.addAttribute("error", "Invalid email or password");
        return "LoginPage";
    }

    //============thuis is for student dashboard ========================
    @GetMapping("/studentDashboard")
    public String studentDashboard(HttpSession session, Model model) {
        // Guard: only students can access
        if (session.getAttribute("LoggedIn") == null ||
                !"STUDENT".equals(session.getAttribute("role"))) {
            return "redirect:/";
        }

        Students student = (Students) session.getAttribute("LoggedIn");
        long id = student.getId();

        model.addAttribute("student",    student);
        model.addAttribute("percentage", attendanceService.getAttendancePercentage(id));
        model.addAttribute("total",      attendanceService.getTotalClasses(id));
        model.addAttribute("present",    attendanceService.getPresentCount(id));
        model.addAttribute("absent",     attendanceService.getTotalClasses(id)
                - attendanceService.getPresentCount(id));
        return "StudentDashboard";
    }

    // ========== Logout ====================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //============ Logout for student ==========
    @GetMapping("/logoutStudent")
    public  String logoutStudent(HttpSession session){
        session.invalidate();
        return "LoginPage";
    }

    //==========logout for teacher mark attendance page=============
    @GetMapping("/logoutTeacher")
    public  String logoutTeacher(HttpSession session){
        session.invalidate();
        return "LoginPage";
    }
}
