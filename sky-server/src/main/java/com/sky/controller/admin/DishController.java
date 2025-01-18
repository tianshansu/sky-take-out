package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ApiOperation("Add new dish")
    public Result<String> addDish(@RequestBody DishDTO dishDTO) {
        log.info("add new dish:{}", dishDTO);

        dishService.add(dishDTO);


        return Result.success();
    }
}
