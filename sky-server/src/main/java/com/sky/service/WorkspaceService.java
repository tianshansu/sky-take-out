package com.sky.service;


import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

public interface WorkspaceService {

    /**
     * bussiness data
     * @return businessDataVO
     */
    BusinessDataVO getBusinessData();

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
