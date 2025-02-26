package com.sky.service;


import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {

    /**
     * bussiness data
     * @return businessDataVO
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * order data
     * @return orderOverViewVO
     */
    OrderOverViewVO getOverviewData();

    /**
     * dishes overview
     * @return dishOverViewVO
     */
    DishOverViewVO getDishOverview();

    /**
     * setmeals overview
     * @return setmealOverViewVO
     */
    SetmealOverViewVO getSetmealOverview();
}
