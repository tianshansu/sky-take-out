package com.sky.service;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.dto.DishDTO;
import com.sky.enumeration.OperationType;

public interface DishService {


    @AutoFill(value = OperationType.INSERT)
    void add(DishDTO dishDTO);
}
