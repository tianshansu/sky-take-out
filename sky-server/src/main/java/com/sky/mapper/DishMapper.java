package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select * from dish where category_id=#{id}")
    List<Dish> getDishByCategoryId(Long id);

    /**
     * add new dish
     *
     * @param dish dish
     */
    @AutoFill(value = OperationType.INSERT)
    void add(Dish dish);


    /**
     * paging dish
     *
     * @param dishPageQueryDTO dishPageQueryDTO
     * @return page
     */
    Page<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * select dish by id
     * @param id dish id
     * @return dishVO
     */
    @Select("select dish.*, category.name as categoryName from dish inner join category on dish.category_id = category.id\n where dish.id=#{id}")
    DishVO selectDishById(Integer id);


    /**
     * batch deletion
     * @param ids dish ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * get dish by dish id
     * @param id dish id
     * @return dish
     */
    @Select("select * from dish where id=#{id}")
    Dish getDishById(Long id);

    /**
     * modify dish
     * @param dish dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void modifyDish(Dish dish);
}
