package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * employee login
     * @param employeeLoginDTO
     * @return employee
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);


    /**
     * Add new employee
     */
    public void addEmployee(EmployeeDTO employeeDTO);

    /**
     * Page employee
     * @param employeePageQueryDTO
     * @return page result
     */
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO);
}
