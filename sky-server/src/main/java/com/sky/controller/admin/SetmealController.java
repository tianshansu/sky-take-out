package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "Setmeal Related")
@Slf4j
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    /**
     * paging setmeal
     * @param setmealPageQueryDTO setmealPageQueryDTO
     * @return PageResult
     */
    @ApiOperation("paging setmeal")
    @GetMapping("/page")
    public Result<PageResult> pagingSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("paging setmeal:{}", setmealPageQueryDTO);
        PageResult page = setmealService.page(setmealPageQueryDTO);

        return Result.success(page);
    }

    /**
     * select setmeal by setmeal id
     * @param id setmeal id
     * @return SetmealVO
     */
    @ApiOperation("select setmeal by setmeal id")
    @GetMapping("/{id}")
    public Result<SetmealVO> selectDishById(@PathVariable Long id) {
        log.info("select setmeal:{}", id);
        SetmealVO setmealVO = setmealService.listBySetmealId(id);
        return Result.success(setmealVO);
    }

    /**
     * add new setmeal
     * @param setmealDTO setmealDTO
     * @return result
     */
    @ApiOperation("add new setmeal")
    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("add setmeal:{}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * modify setmeal
     * @param setmealDTO setmealDTO
     * @return result
     */
    @ApiOperation("modify setmeal")
    @PutMapping
    public Result modifySetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("modify setmeal:{}", setmealDTO);
        setmealService.modifySetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * delete setmeal
     * @param ids setmeal ids - string
     * @return result
     */
    @ApiOperation("delete setmeal")
    @DeleteMapping
    public Result deleteSetmeal(@RequestParam String ids) {
        log.info("delete setmeals:{}", ids);

        //convert string to list
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        setmealService.deleteBatch(idList);

        return Result.success();
    }

    /**
     * set setmeal status
     * @param status new status, 0=disable, 1=enable
     * @param id setmeal id
     * @return result
     */
    @ApiOperation("set setmeal status")
    @PostMapping("/status/{status}")
    public Result setmealStatus(@PathVariable Integer status, @RequestParam Long id) {
        log.info("setmeal id:{}", id);
        log.info("setmeal status:{}", status);

        setmealService.changeStatus(id,status);

        return Result.success();
    }
}
