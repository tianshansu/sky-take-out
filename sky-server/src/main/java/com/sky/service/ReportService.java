package com.sky.service;


import com.sky.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {

    /**
     * turnover statistics
     * @param begin begin date
     * @param end end date
     * @return turnoverReportVO
     */
    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * order report
     * @param begin begin date
     * @param end end date
     * @return orderReportVO
     */
    OrderReportVO orderReport(LocalDate begin, LocalDate end);

    /**
     * user statistics
     * @param begin begin date
     * @param end end date
     * @return userReportVO
     */
    UserReportVO userStatistics(LocalDate begin, LocalDate end);

    /**
     * top 10 sales
     * @param begin begin date
     * @param end end date
     * @return salesTop10ReportVO
     */
    SalesTop10ReportVO top10Sales(LocalDate begin, LocalDate end);

    /**
     * export report
     * @param response HttpServletResponse
     */
    void exportReport(HttpServletResponse response);
}
