package com.example.AttendanceSystem.Repositry;

import com.example.AttendanceSystem.Entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepositry  extends JpaRepository<Students , Long> {

}
