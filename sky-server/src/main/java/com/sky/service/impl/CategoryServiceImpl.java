package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Employee;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * paging category
     *
     * @param queryDTO categoryPageQueryDTO
     * @return paging result
     */
    @Override
    public PageResult page(CategoryPageQueryDTO queryDTO) {
        // Start paging - use PageHelper plugin
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<Category> page = categoryMapper.page(queryDTO);

        long totalPage = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(totalPage, records);
    }

    /**
     * enable, disable category
     *
     * @param status new status, 0 disable, 1 enable
     * @param id     category id
     */
    @Override
    public void changeStatus(Long id, Integer status) {
        Category category = Category.builder().id(id).status(status).updateTime(LocalDateTime.now()).updateUser(BaseContext.getCurrentId()).build();
        categoryMapper.modifyCategory(category);
    }

    /**
     * add new category
     *
     * @param categoryDTO categoryDTO
     */
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .status(0) //status set to disabled when first created
                //.createTime(LocalDateTime.now())
                //.updateTime(LocalDateTime.now())
                //.createUser(BaseContext.getCurrentId())
                //.updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.addCategory(category);
    }

    /**
     * modify a category
     *
     * @param categoryDTO categoryDTO
     */
    @Override
    public void modifyCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                //.updateTime(LocalDateTime.now())
                //.updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.modifyCategory(category);
    }

    @Override
    public void deleteCategory(Long id) {
        //search whether there is any dishes or setmeals under this category

        //select all records in setmeal of this categoryId
        List<Setmeal> setMealList = setMealMapper.getSetMealById(id);
        //if list size >0 (there are setmeals inside), throw exception
        if(setMealList.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        //select all records in dishes of this categoryId
        List<Dish> dishList = dishMapper.getDishById(id);
        //if list size >0 (there are dishes inside), throw exception
        if(setMealList.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //If there are no dishes or setmeals under this category, delete it directly
        categoryMapper.deleteCategory(id);

    }

    /**
     * list by category
     * @param type type
     * @return list
     */
    @Override
    public List<Category> listByCategory(Integer type) {
        List<Category> categories= categoryMapper.listByCategory(type);
        return categories;
    }


}
