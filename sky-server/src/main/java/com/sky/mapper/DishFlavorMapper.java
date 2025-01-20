package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * insert flavor data
     * @param flavors flavors
     */
    void insertBatch(List<DishFlavor> flavors);


    /**
     * select dish by id
     * @param id dish id
     * @return dishVO
     */
    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getFlavorList(Integer id);


    void deleteBatch(List<Long> ids);
}
