package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * emp login
     *
     * @param employeeLoginDTO employeeLoginDTO
     * @return employee
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        // 1. Query the database for the data based on the username
        Employee employee = employeeMapper.getByUsername(username);

        // 2. Handle various exceptions (username not found, incorrect password, account locked)
        if (employee == null) {
            // Account not found
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // Password comparison
        // md5 password compare
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(employee.getPassword())) {
            // Incorrect password
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            // Account is locked
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        // 3. Return the entity object
        return employee;
    }

    /**
     * Add new employee
     *
     * @param employeeDTO employeeDTO
     */
    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        // Copy properties from employeeDTO to employee
        BeanUtils.copyProperties(employeeDTO, employee);

        // Set account info
        employee.setStatus(StatusConstant.ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes())); // Set default password

        // Change to current login user's info
        long threadId = BaseContext.getCurrentId(); // Get current thread ID
        employee.setCreateUser(threadId);
        employee.setUpdateUser(threadId);

        employeeMapper.addEmployee(employee);
    }

    /**
     * Paging employee
     * @param employeePageQueryDTO employeePageQueryDTO
     * @return page result
     */
    @Override
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
        // Start paging - use PageHelper plugin
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.page(employeePageQueryDTO);

        long totalPage = page.getTotal();
        List<Employee> records = page.getResult();
        return new PageResult(totalPage, records);
    }

    /**
     * Enable and disable employee account
     * @param id employee id
     * @param status new status,0=disable,1=enable
     */
    @Override
    public void changeStatus(long id,Integer status) {
        //employeeMapper.changeStatus(id,status);
        //build a new employee object and set status & id
        Employee employee=Employee.builder().status(status).id(id).build();
        employeeMapper.changeStatus(employee);
    }

}
