package com.example.workforce.reference;

import com.example.workforce.common.DepartmentDto;
import com.example.workforce.common.EmployeeBrief;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReferenceDataController {
    @GetMapping("/employees/{employeeId}")
    public EmployeeBrief employee(@PathVariable UUID employeeId) {
        return EmployeeBrief.sample(employeeId);
    }

    @GetMapping("/departments/{departmentId}")
    public DepartmentDto department(@PathVariable UUID departmentId) {
        return DepartmentDto.sample(departmentId);
    }
}