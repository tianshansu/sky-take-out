package com.sky.mapper;


import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * insert batch
     * @param orderDetails orderDetails list
     */
    void insertBatch(List<OrderDetail> orderDetails);

    /**
     * order history
     * @param id order id
     * @return order detail list
     */
    @Select("select * from order_detail where order_id=#{id}")
    List<OrderDetail> findDetailsByOrderId(Long id);
}
