package com.workers.www.repository;

import com.workers.www.model.Salary;
import com.workers.www.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository <Salary, Long> {
    Salary getSalaryById (Long id);
    Salary getSalaryByUser (User user);
    List<Salary> getSalariesByAmount (Double amount, Pageable pageable);
}
