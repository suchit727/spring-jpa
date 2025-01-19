package com.practice.spring_boot_query_practice.service;

import com.practice.spring_boot_query_practice.entity.EmployeeEntity;
import com.practice.spring_boot_query_practice.repository.CriteriaEmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceCriteria {

    private CriteriaEmployeeRepository criteriaEmployeeRepository;

    public List<EmployeeEntity> getAllEmployeeByName(String name) {
        return criteriaEmployeeRepository.getAllEmployeeByName(name);
    }

    public List<EmployeeEntity> getAllEmployee() {
        return criteriaEmployeeRepository.getAllEmployee();
    }
}
