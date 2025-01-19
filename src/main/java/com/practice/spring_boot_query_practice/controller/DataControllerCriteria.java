package com.practice.spring_boot_query_practice.controller;

import com.practice.spring_boot_query_practice.entity.EmployeeEntity;
import com.practice.spring_boot_query_practice.service.DataServiceCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DataControllerCriteria {

    private final DataServiceCriteria dataServiceCriteria;

    @GetMapping("/persons")
    public List<EmployeeEntity> getAllEmployee() {
        return dataServiceCriteria.getAllEmployee();
    }

    @GetMapping(path = "/persons/name")
    public List<EmployeeEntity> getAllEmployeeByName(@RequestParam(name = "name") String name) {
        return dataServiceCriteria.getAllEmployeeByName(name);
    }

}
