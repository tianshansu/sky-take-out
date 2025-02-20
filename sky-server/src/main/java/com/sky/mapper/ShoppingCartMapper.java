package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * dynamic search
     *
     * @param shoppingCart shoppingCart info
     * @return shopping cart obj list
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * add or decrease number by shopping cart id
     *
     * @param shoppingCart shoppingCart
     */
    @Update("update shopping_cart set number = #{number} where id=#{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * insert into shopping_cart table
     * @param shoppingCart shoppingCart obj
     */
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount,create_time) values (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount},#{createTime});")
    void add(ShoppingCart shoppingCart);

    /**
     * delete all in shopping cart
     * @param userId userId
     */
    @Delete("delete from shopping_cart where user_id=#{userId}")
    void clean(Long userId);


    /**
     * delete single element in shopping cart
     * @param shoppingCart shoppingCart
     */
    void sub(ShoppingCart shoppingCart);
}
