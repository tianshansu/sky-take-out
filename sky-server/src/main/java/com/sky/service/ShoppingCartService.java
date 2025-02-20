package com.sky.service;


import com.sky.dto.ShoppingCartDTO;

public interface ShoppingCartService {


    /**
     * add elements to shopping cart
     * @param shoppingCartDTO shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);
}
