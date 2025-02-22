package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
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
     *
     * @param ordersCancelDTO order id
     */
    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    /**
     * place the same order again
     * @param id previous order's id
     */
    void orderRepetition(Long id);

    /**
     * orders conditional search
     * @param ordersPageQueryDTO ordersPageQueryDTO
     * @return page result
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * confirm an order
     *
     * @param ordersConfirmDTO ordersDTO
     */
    void acceptOrder(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * reject an order
     * @param ordersRejectionDTO ordersRejectionDTO
     */
    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * deliver an order
     * @param orderId orderId
     */
    void deliverOrder(Long orderId);

    /**
     * complete an order
     * @param orderId orderId
     */
    void completeOrder(Long orderId);

    /**
     * view order statistics
     * @return order statistics VO
     */
    OrderStatisticsVO orderStatistics();
}
