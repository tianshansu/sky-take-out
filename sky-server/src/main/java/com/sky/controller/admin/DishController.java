package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Dish related
 */
@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Api(tags = "Dish Related")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

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

        //clear cache in redis
        String key="dish_"+dishDTO.getCategoryId();
        cleanCache(key);

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
    public Result<DishVO> selectDishById(@PathVariable Long id) {
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

        //clear cache in redis(delete all data that key starts with dish_)
        cleanCache("*dish_*");


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

        //clear cache in redis(delete all data that key starts with dish_)
        cleanCache("*dish_*");


        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("find dishes by category id")
    public Result<List<DishVO>> listDish(Long categoryId) {
        log.info("find dishes by category id:{}", categoryId);

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE); //only available dishes can be shown on the page
        List<DishVO> dishList = dishService.listWithFlavor(dish);


        return Result.success(dishList);
    }

    /**
     * change dish status
     * @param status new status(0=disable, 1=enable)
     * @param id dish id
     * @return result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("change dish status")
    public Result changeDishStatus(@PathVariable Integer status, @RequestParam Long id){
        log.info("id:{}", id);
        log.info("changeDishStatus:{}", status);

        dishService.changeDishStatus(id, status);

        //clear cache in redis(delete all data that key starts with dish_)
        cleanCache("*dish_*");

        return Result.success();
    }

    private void cleanCache(String pattern){
        Set keys=redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
