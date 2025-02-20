package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.CategoryService;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * add elements to shopping cart
     *
     * @param shoppingCartDTO shoppingCartDTO
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //check whether this element already exists in cart
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId()); //get current user's id
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //if yes, add 1 to the number
        //use list for general use (for example, to find all the contents of a user's shopping cart)
        if (list != null && list.size() > 0) {
            ShoppingCart ele=list.get(0);
            ele.setNumber(ele.getNumber()+1);//add 1 to the original data
            shoppingCartMapper.updateNumberById(ele);//update shopping cart db
        }else{
            //if not, insert a new data to db
            //if it is a dish
            if(shoppingCart.getDishId()!=null&&shoppingCart.getDishId()>0){
                Dish dish=dishMapper.getDishById(shoppingCart.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                //if it is a setmeal
                Setmeal setmeal=setmealMapper.selectSetmealById(shoppingCart.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.add(shoppingCart);
        }
    }
}
