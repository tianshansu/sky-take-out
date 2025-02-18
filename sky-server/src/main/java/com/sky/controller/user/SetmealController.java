package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "Setmeal Related")
@Slf4j
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    /**
     * find setmeal by category id
     * @param categoryId categoryId
     * @return result
     */
    @ApiOperation("find setmeal by category id")
    @GetMapping("/list")
    public Result<List<Setmeal>> list(@RequestParam Long categoryId) {
        log.info("find setmeal by category id:{}", categoryId);

        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE); //only available dishes can be shown on the page

        List<Setmeal> list=setmealService.listByCategoryId(setmeal);
        return Result.success(list);
    }

    /**
     * find dishes by setmeal id
     * @param id id
     * @return result
     */
    @ApiOperation("find dishes by setmeal id")
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> findDishes(@PathVariable Long id) {
        log.info("find dishes by setmeal id:{}", id);

        List<DishItemVO> dishesVO= setmealService.getSetmealDishesById(id);

        return Result.success(dishesVO);
    }
}
