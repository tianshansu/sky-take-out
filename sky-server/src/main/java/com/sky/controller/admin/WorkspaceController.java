package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Api(tags = "admin workspace related")
public class WorkspaceController {
    @Autowired
    private WorkspaceService workspaceService;

    /**
     * bussiness data
     * @return businessDataVO
     */
    @ApiOperation("bussiness data")
    @GetMapping("/businessData")
    public Result<BusinessDataVO> getBusinessData() {
        log.info("get business data");
        LocalDateTime begin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        BusinessDataVO businessDataVO= workspaceService.getBusinessData(begin, end);
        return Result.success(businessDataVO);
    }

    /**
     * order data
     * @return orderOverViewVO
     */
    @ApiOperation("order data")
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> getOverviewOrders() {
        log.info("get overview orders");
        OrderOverViewVO orderOverViewVO=workspaceService.getOverviewData();
        return Result.success(orderOverViewVO);
    }

    /**
     * dishes overview
     * @return dishOverViewVO
     */
    @ApiOperation("dishes overview")
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> getDishesOverview() {
        log.info("get dishes overview");
        DishOverViewVO dishOverViewVO=workspaceService.getDishOverview();
        return Result.success(dishOverViewVO);
    }

    /**
     * setmeals overview
     * @return setmealOverViewVO
     */
    @ApiOperation("setmeals overview")
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> getSetmealOverview() {
        log.info("get setmeals overview");
        SetmealOverViewVO setmealOverViewVO=workspaceService.getSetmealOverview();
        return Result.success(setmealOverViewVO);
    }
}
