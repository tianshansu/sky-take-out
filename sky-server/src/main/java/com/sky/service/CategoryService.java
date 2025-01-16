package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

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

    void deleteCategory(Long id);
}
