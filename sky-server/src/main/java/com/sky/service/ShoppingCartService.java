package com.sky.service;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {


    /**
     * add elements to shopping cart
     * @param shoppingCartDTO shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * list all elements in shopping cart
     *
     * @return
     */
    List<ShoppingCart> list();

    /**
     * delete all in shopping cart
     */
    void cleanAll();

    /**
     * delete single element in shopping cart
     * @param shoppingCartDTO shoppingCartDTO
     */
    void sub(ShoppingCartDTO shoppingCartDTO);
}
