package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * turnover statistics
     *
     * @param begin begin date
     * @param end   end date
     * @return turnoverReportVO
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //construct date list
        List<LocalDate> dateList = dateListGenerator(begin, end);
        String dates = StringUtils.join(dateList, ",");

        //construct turnover list
        List<BigDecimal> turnoverStatistics = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            BigDecimal turnover = orderMapper.turnoverStatistics(beginTime, endTime);
            //if there is no data, set value to 0(otherwise the chart can't work)
            if (turnover == null) {
                turnover = BigDecimal.ZERO;
            }
            turnoverStatistics.add(turnover);
        }

        String turnoverList = StringUtils.join(turnoverStatistics, ",");

        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        turnoverReportVO.setDateList(dates);
        turnoverReportVO.setTurnoverList(turnoverList);
        return turnoverReportVO;
    }

    /**
     * order report
     * @param begin begin date
     * @param end end date
     * @return orderReportVO
     */
    @Override
    public OrderReportVO orderReport(LocalDate begin, LocalDate end) {
        //construct date list
        List<LocalDate> dateList = dateListGenerator(begin, end);
        String dates = StringUtils.join(dateList, ",");

        //total orders list & total finished orders list
        List<Integer> totalOrders = new ArrayList<>();
        List<Integer> totalFinishedOrders = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Integer dailyOrders= getOrderCount(beginTime,endTime, null);
            totalOrders.add(dailyOrders);

            Integer dailyFinishedOrders= getOrderCount(beginTime,endTime, Orders.COMPLETED);
            totalFinishedOrders.add(dailyFinishedOrders);
        }
        String totalOrderList = StringUtils.join(totalOrders, ",");
        Integer totalOrdersCount=totalOrders.stream().reduce(Integer::sum).get();

        String totalFinishedOrderList = StringUtils.join(totalFinishedOrders, ",");
        Integer totalFinishedOrdersCount=totalFinishedOrders.stream().reduce(Integer::sum).get();

        Double orderCompletionRate=0.0;
        if(totalOrdersCount!=0){
            orderCompletionRate=totalFinishedOrdersCount.doubleValue()/totalOrdersCount;
        }

        OrderReportVO orderReportVO = new OrderReportVO();
        orderReportVO.setDateList(dates);
        orderReportVO.setOrderCountList(totalOrderList);
        orderReportVO.setValidOrderCountList(totalFinishedOrderList);
        orderReportVO.setOrderCompletionRate(orderCompletionRate);
        orderReportVO.setValidOrderCount(totalFinishedOrdersCount);
        orderReportVO.setTotalOrderCount(totalOrdersCount);
        return orderReportVO;
    }

    /**
     * user statistics
     * @param begin begin date
     * @param end end date
     * @return userReportVO
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        //generate date list
        List<LocalDate> dateList = dateListGenerator(begin, end);
        String dates = StringUtils.join(dateList, ",");

        //generate statistics (total user)
        List<Integer> totalUsersList= new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Integer userCount =userMapper.getUserCount(null,endTime);
            totalUsersList.add(userCount);
        }
        String totalUsers= StringUtils.join(totalUsersList, ",");

        //generate statistics(daily new users count)
        List<Integer> dailyUsersList=new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Integer userCount =userMapper.getUserCount(beginTime,endTime);
            dailyUsersList.add(userCount);
        }
        String dailyUsers= StringUtils.join(dailyUsersList, ",");

        //construct vo
        UserReportVO userReportVO = new UserReportVO();
        userReportVO.setDateList(dates);
        userReportVO.setTotalUserList(totalUsers);
        userReportVO.setNewUserList(dailyUsers);

        return userReportVO;
    }

    /**
     * top 10 sales
     * @param begin begin date
     * @param end end date
     * @return salesTop10ReportVO
     */
    @Override
    public SalesTop10ReportVO top10Sales(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        //get top 10 sales
        List<GoodsSalesDTO> top10sales=orderDetailMapper.getTop10Names(beginTime,endTime);
        List<String> top10NamesList=top10sales.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> top10AmountList=top10sales.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());

        //construct strings
        String top10SalesString= StringUtils.join(top10NamesList, ",");
        String top10AmountString= StringUtils.join(top10AmountList, ",");

        //construct vo
        SalesTop10ReportVO salesTop10ReportVO = new SalesTop10ReportVO();
        salesTop10ReportVO.setNameList(top10SalesString);
        salesTop10ReportVO.setNumberList(top10AmountString);

        return salesTop10ReportVO;
    }


    private List<LocalDate> dateListGenerator(LocalDate begin, LocalDate end) {
        //construct date list
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        //dateList.add(end);
        return dateList;
    }

    private Integer getOrderCount(LocalDateTime begin, LocalDateTime end,Integer status) {
        //new hash map to store dates and status
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", status);
        return orderMapper.countByMap(map);
    }
}
