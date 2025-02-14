package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Category controller
 */
@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Api(tags = "Category Related")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * paging category
     * @param categoryPageQueryDTO categoryPageQueryDTO
     * @return paging result
     */
    @GetMapping("/page")
    @ApiOperation("paging category")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("paging category:{}" , categoryPageQueryDTO);
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * show list by type
     * @param type type
     * @return page result
     */
    @GetMapping("/list")
    @ApiOperation("show list by type")
    public Result<List<Category>> list(Integer type) {
        log.info("type selected:{}" , type);
        List<Category> categories=categoryService.listByCategory(type);
        return Result.success(categories);
    }

    /**
     * enable, disable category
     * @param status new status, 0 disable, 1 enable
     * @param id category id
     * @return result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("enable, disable category")
    public Result<String> enableDisableCategory(@PathVariable Integer status, @RequestParam Long id) {
        log.info("categpry id: {}m new status:{}" , id,status);
        categoryService.changeStatus(id,status);
        return Result.success();
    }

    /**
     * add new category
     * @param categoryDTO categoryDTO
     * @return result
     */
    @PostMapping
    @ApiOperation("add new category")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("add new category:{}", categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /**
     * modify a category
     * @param categoryDTO categoryDTO
     * @return result
     */
    @PutMapping
    @ApiOperation("modify a category")
    public Result<String> modifyCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("modify category:{}", categoryDTO);
        categoryService.modifyCategory(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("delete a category")
    public Result<String> deleteCategory(@RequestParam Long id){
        log.info("delete category:{}", id);
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
