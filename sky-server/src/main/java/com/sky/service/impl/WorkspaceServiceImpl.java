package com.sky.service.impl;


import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * bussiness data
     *
     * @return businessDataVO
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {

        //get new users count
        Integer newUsers = userMapper.getUserCount(begin, end);

        //get order completion rate
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        Integer totalOrders = orderMapper.countByMap(map);

        //get valid order count
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);

        //get turnover
        BigDecimal turnover = orderMapper.turnoverStatistics(begin, end);
        if (turnover == null)
            turnover = BigDecimal.ZERO;

        Double orderCompletionRate = 0.0;
        Double unitPrice = 0.0;
        if (totalOrders != 0 && completedOrders != 0) {
            //get order completion rate
            orderCompletionRate = completedOrders.doubleValue() / totalOrders.doubleValue();
            //get unit price
            unitPrice = turnover.doubleValue() / completedOrders;
        }

        //construct vo
        BusinessDataVO businessDataVO = new BusinessDataVO();
        businessDataVO.setNewUsers(newUsers);
        businessDataVO.setTurnover(turnover.doubleValue());
        businessDataVO.setOrderCompletionRate(orderCompletionRate);
        businessDataVO.setUnitPrice(unitPrice);
        businessDataVO.setValidOrderCount(completedOrders);

        return businessDataVO;
    }

    /**
     * order data
     *
     * @return orderOverViewVO
     */
    @Override
    public OrderOverViewVO getOverviewData() {
        LocalDateTime begin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        //get total order count
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);
        Integer totalOrders = orderMapper.countByMap(map);

        //get cancelled orders count
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);

        //get completed orders count
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);

        //get confirmed orders count
        map.put("status", Orders.CONFIRMED);
        Integer confirmedOrders = orderMapper.countByMap(map);

        //get waiting orders count
        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = orderMapper.countByMap(map);

        //construct vo
        OrderOverViewVO orderOverViewVO = new OrderOverViewVO();
        orderOverViewVO.setAllOrders(totalOrders);
        orderOverViewVO.setCancelledOrders(cancelledOrders);
        orderOverViewVO.setCompletedOrders(completedOrders);
        orderOverViewVO.setWaitingOrders(waitingOrders);
        orderOverViewVO.setDeliveredOrders(confirmedOrders);

        return orderOverViewVO;
    }

    /**
     * dishes overview
     *
     * @return dishOverViewVO
     */
    @Override
    public DishOverViewVO getDishOverview() {
        //get the number of unavailable dishes
        Map map = new HashMap();
        map.put("status", StatusConstant.DISABLE);
        Integer unavailableDishes = dishMapper.countByMap(map);

        //get the number of available dishes
        map.put("status", StatusConstant.ENABLE);
        Integer availableDishes = dishMapper.countByMap(map);

        DishOverViewVO dishOverViewVO = new DishOverViewVO();
        dishOverViewVO.setDiscontinued(unavailableDishes);
        dishOverViewVO.setSold(availableDishes);

        return dishOverViewVO;
    }

    /**
     * setmeals overview
     * @return setmealOverViewVO
     */
    @Override
    public SetmealOverViewVO getSetmealOverview() {
        //get the number of unavailable setmeals
        Map map = new HashMap();
        map.put("status", StatusConstant.DISABLE);
        Integer unavailableSetmeals = setmealMapper.countByMap(map);

        //get the number of available setmeals
        map.put("status", StatusConstant.ENABLE);
        Integer availableSetmeals = setmealMapper.countByMap(map);

        SetmealOverViewVO setmealOverViewVO = new SetmealOverViewVO();
        setmealOverViewVO.setDiscontinued(unavailableSetmeals);
        setmealOverViewVO.setSold(availableSetmeals);

        return setmealOverViewVO;
    }
}
