package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select * from dish where category_id=#{id}")
    List<Dish> getDishById(Long id);

    /**
     * add new dish
     * @param dish dish
     */
    @AutoFill(value = OperationType.INSERT)
    void add(Dish dish);
}
