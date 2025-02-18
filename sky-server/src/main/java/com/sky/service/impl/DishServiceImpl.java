package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    /**
     * add a new dish
     *
     * @param dishDTO dishDTO
     */
    @Transactional //use transaction
    @Override
    public void add(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //add this dish to dish table
        dishMapper.add(dish);

        //get PK from insert sql query
        Long dishId = dish.getId();

        //if there are flavors, add them to dish_flavor table
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (dishDTO.getFlavors() != null && dishDTO.getFlavors().size() > 0) {
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(dishId);
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * dish paging
     *
     * @param dishPageQueryDTO dishPageQueryDTO
     * @return page result
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.page(dishPageQueryDTO);

        long totalPage = page.getTotal();
        List<DishVO> records = page.getResult();
        return new PageResult(totalPage, records);

    }

    /**
     * select dish by id
     * @param id dish id
     * @return dishVO
     */
    @Override
    public DishVO selectDishById(Long id) {
        DishVO dishVO = dishMapper.selectDishById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.getFlavorList(id);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * delete dishes
     * @param ids dish ids
     * @return result
     */
    @Transactional //involves 2 tables, need to use transaction
    @Override
    public void deleteBatch(List<Long> ids) {
        //check whether all dishes are in disabled status(can be deleted)
        for (Long id : ids) {
            Dish dish = dishMapper.getDishById(id);
            //if current dish is on sale, throw an exception
            if(dish.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //check whether all dishes are not in any setmeals(can be deleted)
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds !=null && setmealIds.size()>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //delete dish
        dishMapper.deleteBatch(ids);

        //delete dishflavor
        dishFlavorMapper.deleteBatch(ids);
    }

    /**
     * modify dish
     * @param dishDTO dishDTO
     * @return result
     */
    @Transactional
    @Override
    public void modifyDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        //modify dish info
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.modifyDish(dish);


        //delete original flavors and add new flavors
        List<Long> id=new ArrayList<Long>(1);
        id.add(dish.getId());
        dishFlavorMapper.deleteBatch(id);

        //if there are flavors, add them to dish_flavor table
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (dishDTO.getFlavors() != null && dishDTO.getFlavors().size() > 0) {
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(dish.getId());
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        //get all dishes in that category
        List<Dish> dishList=dishMapper.list(dish);

        //create empty list to store all dishes that are used to return to frontend
        List<DishVO> dishVOList=new ArrayList<>();

        //append corresponding flavour to dishes
        for (Dish d : dishList) {
            List<DishFlavor> df = dishFlavorMapper.getFlavorList(d.getId());

            DishVO dishVO= new DishVO();
            BeanUtils.copyProperties(d,dishVO);
            dishVO.setFlavors(df);
            dishVOList.add(dishVO);
        }
        //return dish list VO
        return dishVOList;
    }

    /**
     * change dish status
     * @param id dish id
     * @param status new status, 0=disable, 1=enable
     */
    @Override
    public void changeDishStatus(Long id, Integer status) {
        Dish dish=Dish.builder().id(id).status(status).build();
        dishMapper.modifyDish(dish);
    }


}
