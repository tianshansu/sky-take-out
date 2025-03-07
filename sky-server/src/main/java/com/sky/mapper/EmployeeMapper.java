package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * find employee by username
     * @param username username
     * @return employee
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * add new employee
     */
    @Insert("insert into employee (name, username, password, phone, gender, id_number, status,create_time, update_time, create_user, update_user) values (#{name},#{username},#{password},#{phone},#{gender},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT) //autofill - type INSERT
    void addEmployee(Employee employee);


    /**
     * Select employee, page employee
     * @param employeePageQueryDTO employeePageQueryDTO
     * @return page result
     */
    //using dynamic sql query
    Page<Employee> page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * modify employee info by ID
     * @param employee employee object
     */
    //@Update("update employee set status=#{status} where id=#{id}")
    @AutoFill(value = OperationType.UPDATE) //autofill - type UPDATE
    void modifyEmployee(Employee employee);

    /**
     * Get employee info by id
     * @param id employee id
     * @return employee
     */
    @Select("select * from employee where id=#{id};")
    Employee getEmployeeById(Long id);

}
