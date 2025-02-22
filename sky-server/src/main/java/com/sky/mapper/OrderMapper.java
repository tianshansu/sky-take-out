package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
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
     * orders conditional search
     * @param ordersPageQueryDTO ordersPageQueryDTO
     * @return order paging
     */
    Page<Orders> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     *confirm an order
     * @param order order
     */
    void modifyOrder(Orders order);

    /**
     * view order statistics
     * @param status status
     * @return count(integer)
     */
    @Select("select count(id) from orders where status=#{status}")
    Integer statistics(Integer status);

    /**
     * get all orders by status and order time
     * @param status status
     * @param orderTime orderTime
     * @return orders list
     */
    @Select("select * from orders where status=#{status} and order_time < #{orderTime}")
    List<Orders> checkForTimeoutOrder(Integer status, LocalDateTime orderTime);
}
