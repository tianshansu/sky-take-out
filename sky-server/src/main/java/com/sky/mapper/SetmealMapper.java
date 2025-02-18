package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    List<Setmeal> list(Setmeal setmeal);

    /**
     * paging setmeal
     *
     * @param setmealPageQueryDTO setmealPageQueryDTO
     * @return page
     */
    Page<SetmealVO> page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * selectSetmealById
     * @param id setmeal id
     * @return
     */
    @Select("select * from setmeal where id=#{id}")
    Setmeal selectSetmealById(Long id);

    /**
     * add new setmeal
     * @param setmeal setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void addSetmeal(Setmeal setmeal);

    /**
     * modify setmeal
     * @param setmeal setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    void modifySetmeal(Setmeal setmeal);

    /**
     * delete batch
     * @param idList idList
     */
    void deleteBatch(List<Long> idList);
}
