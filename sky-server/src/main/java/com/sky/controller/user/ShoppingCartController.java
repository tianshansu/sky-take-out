package com.sky.controller.user;

import com.sky.dto.DishDTO;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
@Api(tags = "Shopping Cart Related")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * add elements to shopping cart
     * @param shoppingCartDTO shoppingCartDTO
     * @return result
     */
    @ApiOperation("add elements to shopping cart")
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("shoppingCartDTO : {}", shoppingCartDTO);

        shoppingCartService.add(shoppingCartDTO);

        return Result.success();
    }

    /**
     * view shopping cart
     * @return result-shopping cart list
     */
    @ApiOperation("view shopping cart")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        log.info("listing shopping cart");

        List<ShoppingCart> shoppingCartList= shoppingCartService.list();

        return Result.success(shoppingCartList);
    }
}
