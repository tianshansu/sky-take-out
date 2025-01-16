package com.sky.mapper;

import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealMapper {

    @Select("select * from setmeal where category_id=#{id}")
    List<Setmeal> getSetMealById(Long id);
}
