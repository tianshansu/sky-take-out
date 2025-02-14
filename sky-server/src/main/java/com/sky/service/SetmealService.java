package com.sky.service;

import com.sky.entity.Setmeal;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * listByCategoryId
     * @param setmeal setmeal
     * @return List<SetmealVO>
     */
    List<Setmeal> listByCategoryId(Setmeal setmeal);
}
