package com.practice.spring_boot_query_practice.service;

import com.practice.spring_boot_query_practice.entity.DepartmentEntity;
import com.practice.spring_boot_query_practice.entity.Employee;
import com.practice.spring_boot_query_practice.entity.EmployeeEntity;
import com.practice.spring_boot_query_practice.repository.CriteriaEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataServiceCriteria {

    private final CriteriaEmployeeRepository criteriaEmployeeRepository;

    public List<Employee> getAllEmployeeByName(String name) {
        return criteriaEmployeeRepository.getAllEmployeeByName(name);
    }

    public List<Employee> getAllEmployee() {
        return criteriaEmployeeRepository.getAllEmployee();
    }

    public List<Employee> getAllEmployeesByName(String name, Integer salary) {
        return criteriaEmployeeRepository.getAllEmployeesByName(name, salary);
    }

    public List<Employee> getAllEmployeeSortedBySalary() {
        return criteriaEmployeeRepository.getAllEmployeeSortedBySalary();
    }

    public List<Employee> getAllEmployeeSortedByName() {
        return  criteriaEmployeeRepository.getAllEmployeeSortedByName();
    }

    public List<Employee> getAllEmployeeInPaginatedForm(Integer page, Integer size) {
        return criteriaEmployeeRepository.getAllEmployeeInPaginatedForm(page,size);
    }

    public List<EmployeeEntity> getAllEmployeesByDepartmentName(String departmentName) {
        return criteriaEmployeeRepository.getAllEmployeesByDepartmentName(departmentName);
    }

    public Integer getHighestPaidEmployee() {
        return criteriaEmployeeRepository.getHighestPaidEmployee();
    }

    public List<DepartmentEntity> getDepartmentStats(String name) {
        return criteriaEmployeeRepository.getDepartmentStats(name);
    }

    public List<Employee> getEmployeeAboveAverageSalary() {
        return criteriaEmployeeRepository.getEmployeeAboveAverageSalary();
    }

    public List<Employee> getEmployeeWithDynamicQuery(String name, Integer salary) {
        return criteriaEmployeeRepository.getEmployeeWithDynamicQuery(name,salary);
    }

    public Long getAllEmployeeByName(){
        return criteriaEmployeeRepository.getAllEmployeeByName();
    }
}
