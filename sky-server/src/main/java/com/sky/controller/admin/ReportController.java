package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RestController
@Api(tags = "report related")
@Slf4j
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * turnover statistics
     *
     * @param begin begin date
     * @param end   end date
     * @return turnoverReportVO
     */
    @ApiOperation("turn over statistics")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("begin: {}, end: {}", begin, end);
        TurnoverReportVO turnoverReportVO = reportService.turnoverStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }

    /**
     * order report
     * @param begin begin date
     * @param end end date
     * @return orderReportVO
     */
    @ApiOperation("order statistics")
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("begin: {}, end: {}", begin, end);
        OrderReportVO orderReportVO = reportService.orderReport(begin, end);
        return Result.success(orderReportVO);
    }


    /**
     * user statistics
     * @param begin begin date
     * @param end end date
     * @return userReportVO
     */
    @ApiOperation("user statistics")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("begin: {}, end: {}", begin, end);
        UserReportVO userReportVO = reportService.userStatistics(begin,end);
        return Result.success(userReportVO);
    }

    /**
     * top 10 sales
     * @param begin begin date
     * @param end end date
     * @return salesTop10ReportVO
     */
    @ApiOperation("top 10 sales")
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10Sales(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("begin: {}, end: {}", begin, end);
        SalesTop10ReportVO salesTop10ReportVO=reportService.top10Sales(begin,end);
        return Result.success(salesTop10ReportVO);
    }

    /**
     * export report
     * @param response HttpServletResponse
     */
    @ApiOperation("export report")
    @GetMapping("/export")
    public void exportReport(HttpServletResponse response) {
        log.info("export report");
        reportService.exportReport(response);
    }

}
