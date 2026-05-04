package com.example.AttendanceSystem.Service;

import com.example.AttendanceSystem.Entity.Students;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    public void markAttendance(LocalDate localDate, List<Long> presentIds, List<Students> allStudent);
    public double getAttendancePercentage(long studentId);
    // ── Total classes held ───────────────────────────────────────
    public long getTotalClasses(long studentId);
    // ── Total present days ───────────────────────────────────────
    public long getPresentCount(long studentId);
    // ── Check if attendance already marked for this date ─────────
    public boolean isAlreadyMarked(LocalDate date);
}
