package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setMealMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

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

    /**
     * paging setmeals
     *
     * @param setmealPageQueryDTO setmealPageQueryDTO
     * @return page result list
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setMealMapper.page(setmealPageQueryDTO);

        long totalPage = page.getTotal();
        List<SetmealVO> records = page.getResult();
        return new PageResult(totalPage, records);
    }

    /**
     * listBySetmealId
     *
     * @param id setmeal id
     * @return setmeal vo
     */
    @Override
    public SetmealVO listBySetmealId(Long id) {
        //select setmeal info
        Setmeal setmeal = setMealMapper.selectSetmealById(id);

        //find all dishes belong to this setmeal id
        List<SetmealDish> dishes = setmealDishMapper.getAllDishesBySetmealId(id);

        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(dishes);

        return setmealVO;
    }

    /**
     * add setmeal
     *
     * @param setmealDTO setmealDTO
     */
    @Override
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setMealMapper.addSetmeal(setmeal);


        //get PK from insert sql query
        Long setmealId = setmeal.getId();

        //get all dishes from setmealDTO
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        //add dishes in setmeal_dish table with their setmeal id
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }

        setmealDishMapper.addBatch(setmealDishes);
    }

    /**
     * modify setmeal
     * @param setmealDTO setmealDTO
     */
    @Override
    public void modifySetmeal(SetmealDTO setmealDTO) {
        //modify info in setmeal table
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setMealMapper.modifySetmeal(setmeal);

        //delete all related dishes in setmeal_dish table
        List<Long> ids = new ArrayList<>();
        ids.add(setmeal.getId());
        setmealDishMapper.deleteBatch(ids);

        //add all new dishes to setmeal_dish table
        List<SetmealDish> dishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : dishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        setmealDishMapper.addBatch(dishes);
    }

    /**
     * delete batch
     * @param idList idList - ids of setmeals want to be deleted
     */
    @Override
    public void deleteBatch(List<Long> idList) {
        setMealMapper.deleteBatch(idList);
    }

    /**
     * change setmeal status
     * @param id setmeal id
     * @param status status
     */
    @Override
    public void changeStatus(Long id, Integer status) {
        Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
        setMealMapper.modifySetmeal(setmeal);
    }

    /**
     * get all dishes in setmeal
     * @param id setmeal id
     */
    @Override
    public List<DishItemVO> getSetmealDishesById(Long id) {
        List<SetmealDish> dishes=  setmealDishMapper.getAllDishesBySetmealId(id);
        List<DishItemVO> dishItemVOs = new ArrayList<>();
        for (SetmealDish setmealDish : dishes) {
            DishItemVO dishItemVO = new DishItemVO();
            BeanUtils.copyProperties(setmealDish, dishItemVO);
            System.out.println(dishItemVO.toString());
            dishItemVOs.add(dishItemVO);
        }
        return dishItemVOs;
    }
}
