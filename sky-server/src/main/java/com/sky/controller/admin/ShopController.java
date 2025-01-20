package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@Api(tags = "Shop Related")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {

    public static final String KEY="SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * set shop status
     * @param status status 1=open 0=closed
     * @return result
     */
    @PutMapping("/{status}")
    @ApiOperation("set shop status")
    public Result<String> setShopStatus(@PathVariable Integer status) {
        log.info("set shop status:{}", status);
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    /**
     * get current shop status
     * @return result
     */
    @GetMapping("/status")
    @ApiOperation("get current shop status")
    public Result<Integer> getShopStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("current shop status:{}", status==1? "open":"closed");
        return Result.success(status);
    }
}
