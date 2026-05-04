package com.example.AttendanceSystem.Controller;

import com.example.AttendanceSystem.Entity.Students;
import com.example.AttendanceSystem.Entity.Teacher;
import com.example.AttendanceSystem.Service.AttendanceService;
import com.example.AttendanceSystem.Service.StudentService;
import com.example.AttendanceSystem.Service.TeacherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String AddStudentPage(Model model) {
        model.addAttribute("Data", new Students());
        return "LoginPage";
    }

    @GetMapping("/addstudents")
    public String addStudentPageFromMark(Model model) {
        model.addAttribute("studentData", new Students());
        return "AddStudent";
    }

    @GetMapping("/markPage")
    public String markAttendance(Model model) {
        model.addAttribute("students", studentService.getAllStudent());
        model.addAttribute("today", java.time.LocalDate.now().toString());
        return "markAttendance";
    }

    @PostMapping("/MarkAttendance")
    public String SubmitAttendace(@RequestParam("date") String date,
                                  @RequestParam(value = "presentIds", required = false)
                                  List<Long> presentIds, RedirectAttributes redirectAttributes) {
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

    @PostMapping("/register")
    public String LoginPage(@ModelAttribute("Data") Teacher teacher, Model model) {
        boolean status = teacherService.saveTeacher(teacher);
        if (status) {
            model.addAttribute("success", "Successfully... ");
        } else {
            model.addAttribute("error", "Something went wrong... ");
        }
        return "redirect:/";
    }

    @GetMapping("/reportAttendance")
    public String openReport(Model model) {
        List<Students> students = studentService.getAllStudent();
        model.addAttribute("students", students);
        model.addAttribute("percentageMap", attendanceService.getAttendancePercentageMap(students));
        return "Report";
    }

    @PostMapping("/addForm")
    public String saveStudentData(@ModelAttribute("studentData") Students students,
                                  HttpSession session, RedirectAttributes redirectAttributes) {
        boolean status = studentService.saveStudent(students);
        if (status) {
            redirectAttributes.addFlashAttribute("succesMSG", "Successfully... ");
        } else {
            redirectAttributes.addFlashAttribute("errorMSG", "Something went wrong... ");
        }
        return "redirect:/addstudents";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              @RequestParam("role") String role,
                              HttpSession session, Model model) {
        if (role.equals("teacher")) {
            Teacher found = teacherService.validTeacher(email, password);
            if (found != null) {
                session.setAttribute("LoggedIn", found);
                session.setAttribute("role", "TEACHER");
                session.setAttribute("userName", found.getName());
                return "redirect:/markPage";
            }
        } else if (role.equals("student")) {
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

    @GetMapping("/studentDashboard")
    public String studentDashboard(HttpSession session, Model model) {
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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/logoutStudent")
    public String logoutStudent(HttpSession session) {
        session.invalidate();
        return "LoginPage";
    }

    @GetMapping("/logoutTeacher")
    public String logoutTeacher(HttpSession session) {
        session.invalidate();
        return "LoginPage";
    }
}