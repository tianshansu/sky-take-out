package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * emp management
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * Login
     *
     * @param employeeLoginDTO employeeLoginDTO
     * @return result
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("emp login：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //generate jwt token after login
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        // Encapsulate an EmployeeLoginVO object for the frontend using the builder pattern
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * logout
     *
     * @return result
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * Add new employee
     * @param employeeDTO employeeDTO
     * @return result
     */
    @PostMapping
    @ApiOperation("Add new employee")
    public Result<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("add new employee:{}", employeeDTO);
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    /**
     * Paging employee
     * @param employeePageQueryDTO employeePageQueryDTO
     * @return result
     */
    @GetMapping("/page")
    @ApiOperation("Show employee list")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("page employee:{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.page(employeePageQueryDTO);
        return Result.success(pageResult);

    }


    /**
     * Enable and disable employee account
     * @param status new status, 0=disable,1=enable
     * @param id employee id
     * @return result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Enable and disable employee account")
    public Result changeEmployeeStatus(@PathVariable Integer status, @RequestParam long id) {
        log.info("change employee {} status to:{}", id,status);
        employeeService.changeStatus(id,status);
        return Result.success();
    }

}
