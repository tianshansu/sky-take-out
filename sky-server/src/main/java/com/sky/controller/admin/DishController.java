package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Dish related
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish Related")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * Add new dish
     * @param dishDTO dishDTO
     * @return result
     */
    @PostMapping
    @ApiOperation("Add new dish")
    public Result<String> addDish(@RequestBody DishDTO dishDTO) {
        log.info("add new dish:{}", dishDTO);

        dishService.add(dishDTO);


        return Result.success();
    }

    /**
     * paging dishes
     * @param dishPageQueryDTO dishPageQueryDTO
     * @return page result list
     */
    @GetMapping("/page")
    @ApiOperation("paging dishes")
    public Result<PageResult> pageDish(DishPageQueryDTO dishPageQueryDTO) {
        log.info("pageDish:{}", dishPageQueryDTO);
        PageResult pageResult= dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * select dish by id
     * @param id dish id
     * @return dishVO
     */
    @GetMapping("/{id}")
    @ApiOperation("select dish by id")
    public Result<DishVO> selectDishById(@PathVariable Integer id) {
        log.info("selectDishById:{}", id);
        DishVO dishVO = dishService.selectDishById(id);
        return Result.success(dishVO);
    }

    /**
     * delete dishes
     * @param ids dish ids
     * @return result
     */
    @DeleteMapping
    @ApiOperation("delete dishes")
    public Result<String> deleteDish(@RequestParam List<Long> ids) {
        log.info("deleteDish:{}", ids);

        dishService.deleteBatch(ids);

        return Result.success();
    }

    /**
     * modify dish
     * @param dishDTO dishDTO
     * @return result
     */
    @PutMapping
    @ApiOperation("modify dish")
    public Result<String> modifyDish(@RequestBody DishDTO dishDTO) {
        log.info("modify dish:{}", dishDTO);

        dishService.modifyDish(dishDTO);
        return Result.success();
    }
}
