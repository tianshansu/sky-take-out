package com.sky.service;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {


    /**
     * add new dish
     * @param dishDTO dishDTO
     */
    @AutoFill(value = OperationType.INSERT)
    void add(DishDTO dishDTO);


    /**
     * paging dishes
     * @param dishPageQueryDTO dishPageQueryDTO
     * @return page result list
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);


    /**
     * select dish by id
     * @param id dish id
     * @return dishVO
     */
    DishVO selectDishById(Long id);

    /**
     * delete dishes
     * @param ids dish ids
     * @return result
     */
    void deleteBatch(List<Long> ids);

    /**
     * modify dish
     * @param dishDTO dishDTO
     * @return result
     */
    void modifyDish(DishDTO dishDTO);

    /**
     * list dishes with flavors
     * @param dish dish
     * @return dishVO list
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * change dish status
     * @param id dish id
     * @param status new status, 0=disable, 1=enable
     */
    void changeDishStatus(Long id, Integer status);
}
