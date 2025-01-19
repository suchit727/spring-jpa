package com.practice.spring_boot_query_practice.controller;

import com.practice.spring_boot_query_practice.entity.DepartmentEntity;
import com.practice.spring_boot_query_practice.entity.Employee;
import com.practice.spring_boot_query_practice.entity.EmployeeEntity;
import com.practice.spring_boot_query_practice.service.DataServiceCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class DataControllerCriteria {

    private final DataServiceCriteria dataServiceCriteria;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return dataServiceCriteria.getAllEmployee();
    }

    // 2. Get employees by name
    @GetMapping("/search-name")
    public List<Employee> getEmployeesByName(@RequestParam(value = "name") String name) {
        return dataServiceCriteria.getAllEmployeeByName(name);
    }

    // 3. Get employees by name and salary
    @GetMapping("/filter")
    public List<Employee> getEmployeesByNameAndSalary(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "salary", required = false) Integer salary) {
        return dataServiceCriteria.getAllEmployeesByName(name, salary);
    }

    // 4. Get all employees sorted by salary
    @GetMapping("/sorted-by-salary")
    public List<Employee> getAllEmployeesSortedBySalary() {
        return dataServiceCriteria.getAllEmployeeSortedBySalary();
    }

    @GetMapping("/sorted-by-name")
    public List<Employee> getAllEmployeesSortedByName() {
        return dataServiceCriteria.getAllEmployeeSortedByName();
    }

    // 2. Get employees in a paginated form
    @GetMapping("/paginated")
    public List<Employee> getAllEmployeesInPaginatedForm(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        return dataServiceCriteria.getAllEmployeeInPaginatedForm(page, size);
    }

    // 3. Get employees by department name
    @GetMapping("/by-department")
    public List<EmployeeEntity> getEmployeesByDepartmentName(@RequestParam("department") String departmentName) {
        return dataServiceCriteria.getAllEmployeesByDepartmentName(departmentName);
    }

    // 4. Count distinct employee names
    @GetMapping("/distinct-name-count")
    public Long getDistinctEmployeeNameCount() {
        return dataServiceCriteria.getAllEmployeeByName();
    }

    // 5. Get the highest-paid employee's salary
    @GetMapping("/highest-salary")
    public Integer getHighestPaidEmployeeSalary() {
        return dataServiceCriteria.getHighestPaidEmployee();
    }

    // 6. Get department statistics
    @GetMapping("/department-stats")
    public List<DepartmentEntity> getDepartmentStats(@RequestParam("name") String name) {
        return dataServiceCriteria.getDepartmentStats(name);
    }

    // 7. Get employees with above-average salary
    @GetMapping("/above-average-salary")
    public List<Employee> getEmployeesAboveAverageSalary() {
        return dataServiceCriteria.getEmployeeAboveAverageSalary();
    }

    // 8. Get employees using a dynamic query
    @GetMapping("/search")
    public List<Employee> getEmployeesWithDynamicQuery(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "salary", required = false) Integer salary) {
        return dataServiceCriteria.getEmployeeWithDynamicQuery(name, salary);
    }
}
