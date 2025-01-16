package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Category controller
 */
@RestController
@RequestMapping("/admin/category")
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
     * show list by type or name
     * @param categoryPageQueryDTO categoryPageQueryDTO
     * @return page result
     */
    @GetMapping("/list")
    @ApiOperation("show list by type or name")
    public Result<PageResult> list(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("type selected:{}" , categoryPageQueryDTO);
        PageResult pageResult=categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * enable, disable category
     * @param status new status, 0 disable, 1 enable
     * @param id category id
     * @return result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("enable, disable category")
    public Result enableDisableCategory(@PathVariable Integer status, @RequestParam Long id) {
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
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
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
    public Result modifyCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("modify category:{}", categoryDTO);
        categoryService.modifyCategory(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("delete a category")
    public Result deleteCategory(@RequestParam Long id){
        log.info("delete category:{}", id);
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
