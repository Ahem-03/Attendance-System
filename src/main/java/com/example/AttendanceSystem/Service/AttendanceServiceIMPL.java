package com.example.AttendanceSystem.Service;

import com.example.AttendanceSystem.Entity.Attendance;
import com.example.AttendanceSystem.Entity.Students;
import com.example.AttendanceSystem.Repositry.AttendanceRepository;
import com.example.AttendanceSystem.Repositry.StudentRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceServiceIMPL implements AttendanceService{

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepositry studentRepositry;
    @Override
    public void markAttendance(LocalDate localDate, List<Long> presentIds, List<Students> allStudent) {
        for (Students s : allStudent) {
            Attendance a = new Attendance();
            a.setStudent(s);
            a.setDate(localDate);
            a.setStatus(presentIds != null && presentIds.contains(s.getId())
                    ? "PRESENT" : "ABSENT");
            attendanceRepository.save(a);
        }

    }

    @Override
    public double getAttendancePercentage(long studentId) {
        long total   = attendanceRepository.countByStudentId(studentId);
        long present = attendanceRepository.countByStudentIdAndStatus(studentId, "PRESENT");
        if (total == 0) return 0.0;
        return Math.round((present * 100.0 / total) * 10.0) / 10.0;
    }

    @Override
    public long getTotalClasses(long studentId) {
        return  attendanceRepository.countByStudentId(studentId);
    }

    @Override
    public long getPresentCount(long studentId) {
        return attendanceRepository.countByStudentIdAndStatus(studentId, "Present");
    }

    @Override
    public boolean isAlreadyMarked(LocalDate date) {
        return attendanceRepository.existsByDate(date);
    }
}
