package com.sky.service.impl;

import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setMealMapper;

    /**
     * listByCategoryId
     *
     * @param setmeal setmeal
     * @return List<SetmealVO>
     */
    @Override
    public List<Setmeal> listByCategoryId(Setmeal setmeal) {
        List<Setmeal> list = setMealMapper.list(setmeal);

        return list;
    }
}
