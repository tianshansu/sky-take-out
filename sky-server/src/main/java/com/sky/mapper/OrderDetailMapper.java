package com.sky.mapper;


import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * insert batch
     *
     * @param orderDetails orderDetails list
     */
    void insertBatch(List<OrderDetail> orderDetails);

    /**
     * order history
     *
     * @param id order id
     * @return order detail list
     */
    @Select("select * from order_detail where order_id=#{id}")
    List<OrderDetail> findDetailsByOrderId(Long id);

    /**
     * top 10 sales info (name+sum)
     * @param begin begin time
     * @param end end time
     * @return goodsSalesDTO list
     */
    @Select("select od.name, sum(od.number) number from order_detail od inner join orders o on od.order_id=o.id where o.status=5 and o.order_time between #{begin} and #{end} group by od.name order by sum(od.number) desc limit 0,10")
    List<GoodsSalesDTO> getTop10Names(LocalDateTime begin, LocalDateTime end);
}
