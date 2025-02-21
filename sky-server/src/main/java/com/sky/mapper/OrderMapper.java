package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMapper {


    /**
     * user submit an order
     * @param order order
     */
    void submit(Orders order);

    /**
     * paging orders
     * @param order order
     * @return orders paging obj
     */
    Page<Orders> page(Orders order);

    /**
     * view a single order detail
     * @param id order id
     * @return orders
     */
    @Select("select * from orders where id=#{id}")
    Orders findOrderById(Long id);

    /**
     * cancel an order
     * @param id order id
     */
    @Update("update orders set status=6 where id=#{id}")
    void cancelOrder(Long id);
}
