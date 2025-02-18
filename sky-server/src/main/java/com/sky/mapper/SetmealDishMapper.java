package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * getSetmealIdsByDishIds
     * @param dishIds dishIds
     * @return setmealId
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * getAllDishesBySetmealId
     * @param id setmeal id
     * @return SetmealDish list
     */
    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> getAllDishesBySetmealId(Long id);

    /**
     * addBatch
     * @param setmealDishes setmealDishes
     */
    void addBatch(List<SetmealDish> setmealDishes);

    /**
     * delete batch
     * @param ids setmeal ids
     */
    void deleteBatch(List<Long> ids);
}
