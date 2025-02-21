package com.sky.service;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    /**
     * user places order
     * @param ordersSubmitDTO ordersSubmitDTO
     * @return order submit VO
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);


    /**
     * order history
     * @param page page
     * @param pageSize pageSize
     * @param status order status
     * @return page result
     */
    PageResult page(Integer page, Integer pageSize, Integer status);

    /**
     * view a single order detail
     * @param id order id
     * @return orderVO
     */
    OrderVO orderDetail(Long id);

    /**
     * cancel an order
     * @param id order id
     */
    void cancelOrder(Long id);

    /**
     * place the same order again
     * @param id previous order's id
     */
    void orderRepetition(Long id);
}
