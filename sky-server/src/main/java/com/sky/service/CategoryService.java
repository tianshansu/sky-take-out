package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * paging category
     * @param queryDTO categoryPageQueryDTO
     * @return paging result
     */
    PageResult page(CategoryPageQueryDTO queryDTO);


    /**
     * enable, disable category
     * @param status new status, 0 disable, 1 enable
     * @param id category id
     */
    void changeStatus(Long id, Integer status);


    /**
     * add new category
     * @param categoryDTO categoryDTO
     */
    void addCategory(CategoryDTO categoryDTO);

    /**
     * modify a category
     * @param categoryDTO categoryDTO
     */
    void modifyCategory(CategoryDTO categoryDTO);

    /**
     * delete a category
     * @param id category id
     */
    void deleteCategory(Long id);


    /**
     * list by category
     * @param type type
     * @return list
     */
    List<Category> listByCategory(Integer type);
}
