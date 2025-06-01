package com.workers.www.service;

import com.workers.www.dto.CreateSalaryDto;
import com.workers.www.model.Salary;
import com.workers.www.model.User;
import com.workers.www.repository.SalaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SalaryService {
    private final SalaryRepository salaryRepository;
    private final UserService userService;

    public List<Salary> getAllSalaries () {
        return this.salaryRepository.findAll();
    }

    public Page<Salary> getAllSalariesPaginated (int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.salaryRepository.findAll(pageable);
    }

    public Salary getSalaryById (Long id) {
        return this.salaryRepository.getSalaryById(id);
    }

    public Salary getSalaryByUser (Long user_id) {
        User user = this.userService.getUserById(user_id);
        if (user != null) {
            return this.salaryRepository.getSalaryByUser(user);
        }
        return null;
    }

    public Boolean deleteSalaryById (Long salary_id) {
        Salary salary = this.getSalaryById(salary_id);
        if (salary != null) {
            this.salaryRepository.delete(salary);
            return true;
        }
        return false;
    }

    public Salary updateSalaryById (Long salary_id, CreateSalaryDto createSalaryDto) {
        Salary salary = this.getSalaryById(salary_id);
        if (salary == null) {
            return null;
        }

        User user = this.userService.getUserById(createSalaryDto.getUser_id());
        if (user == null) {
            return null;
        }

        salary.setUser(user);
        salary.setAmount(createSalaryDto.getAmount());
        salary.setDescription(createSalaryDto.getDescription());
        salary.setDuration(createSalaryDto.getDuration());

        return this.salaryRepository.save(salary);
    }

    public Salary createSalary (CreateSalaryDto createSalaryDto) {
        User user = this.userService.getUserById(createSalaryDto.getUser_id());
        if (user != null) {
            Salary newSalary = new Salary(user, createSalaryDto.getAmount(), createSalaryDto.getDescription(), createSalaryDto.getDuration());
            return this.salaryRepository.save(newSalary);
        }
        return null;
    }
}
