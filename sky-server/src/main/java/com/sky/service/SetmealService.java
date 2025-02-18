package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * listByCategoryId
     * @param setmeal setmeal
     * @return List<SetmealVO>
     */
    List<Setmeal> listByCategoryId(Setmeal setmeal);

    /**
     * paging setmeals
     * @param setmealPageQueryDTO setmealPageQueryDTO
     * @return page result list
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * listBySetmealId
     * @param id setmeal id
     * @return setmeal vo
     */
    SetmealVO listBySetmealId(Long id);

    /**
     * add setmeal
     * @param setmealDTO setmealDTO
     */
    void addSetmeal(SetmealDTO setmealDTO);

    /**
     * modify setmeal
     * @param setmealDTO setmealDTO
     */
    void modifySetmeal(SetmealDTO setmealDTO);

    /**
     * delete batch
     * @param idList idList - ids of setmeals want to be deleted
     */
    void deleteBatch(List<Long> idList);

    /**
     * change setmeal status
     * @param id setmeal id
     * @param status status
     */
    void changeStatus(Long id, Integer status);
}
