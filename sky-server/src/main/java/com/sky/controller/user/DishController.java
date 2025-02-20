package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "Dish Related")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * find dishes by category id
     * @param categoryId categoryId
     * @return dishVO list
     */
    @GetMapping("/list")
    @ApiOperation("find dishes by category id")
    public Result<List<DishVO>> listDish(Long categoryId) {
        log.info("find dishes by category id:{}", categoryId);

        //construct redis key
        String key = "dish_" + categoryId;

        //check in redis cache whether dish info is inside - force cast
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        //if yes, return info in chache
        if(list!=null&&list.size()>0){
            //System.out.println("found cache");
            return Result.success(list);
        }

        //System.out.println("no cache");
        //if no, search in db and store info in redis cache
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE); //only available dishes can be shown on the page

        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);//store info in redis

        return Result.success(list);
    }
}
