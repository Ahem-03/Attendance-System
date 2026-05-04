package com.example.AttendanceSystem.Repositry;

import com.example.AttendanceSystem.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    long countByStudentId(long studentId);

    long countByStudentIdAndStatus(long studentId, String status);

    boolean existsByDate(java.time.LocalDate date);
}