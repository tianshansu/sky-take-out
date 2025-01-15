package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * find employee by username
     * @param username
     * @return employee
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * add new employee
     */
    @Insert("insert into employee (name, username, password, phone, gender, id_number, status,create_time, update_time, create_user, update_user) values (#{name},#{username},#{password},#{phone},#{gender},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addEmployee(Employee employee);


    /**
     * Select employee, page employee
     * @param employeePageQueryDTO
     * @return page result
     */
    //using dynamic sql query
    Page<Employee> page(EmployeePageQueryDTO employeePageQueryDTO);
}
