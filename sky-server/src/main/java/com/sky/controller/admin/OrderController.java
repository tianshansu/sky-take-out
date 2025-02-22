package com.sky.controller.admin;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "Admin order related")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * orders conditional search
     * @param ordersPageQueryDTO ordersPageQueryDTO
     * @return page result
     */
    @ApiOperation("orders conditional search")
    @GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("ordersPageQueryDTO:{}", ordersPageQueryDTO);
        PageResult pageResult=orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * get order detaiils
     * @param id order id
     * @return orderVO
     */
    @ApiOperation("get order details")
    @GetMapping("/details/{id}")
    public Result<OrderVO> orderDetails(@PathVariable Long id) {
        log.info("id:{}", id);
        OrderVO orderVO=orderService.orderDetail(id);
        return Result.success(orderVO);
    }

   /* @ApiOperation("payment")
    @PutMapping("/payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {
        log.info("ordersPaymentDTO:{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO=orderService.payment(ordersPaymentDTO);
        return Result.success(orderPaymentVO);
    }*/

    /**
     * confirm an order
     * @param ordersConfirmDTO ordersConfirmDTO
     * @return result
     */
    @ApiOperation("confirm an order")
    @PutMapping("/confirm")
    public Result orderConfirmaton(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("ordersConfirmDTO:{}", ordersConfirmDTO);
        orderService.acceptOrder(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * reject an order
     * @param ordersRejectionDTO ordersRejectionDTO
     * @return result
     */
    @ApiOperation("reject an order")
    @PutMapping("/rejection")
    public Result orderRejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("ordersDTO:{}", ordersRejectionDTO);
        orderService.rejectOrder(ordersRejectionDTO);
        return Result.success();
    }


    /**
     * cancel an order
     * @param ordersCancelDTO ordersCancelDTO
     * @return result
     */
    @ApiOperation("cancel an order")
    @PutMapping("/cancel")
    public Result orderCancellation(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        log.info("ordersDTO:{}", ordersCancelDTO);
        orderService.cancelOrder(ordersCancelDTO);
        return Result.success();
    }

    /**
     * deliver an order
     * @param id orderId
     * @return result
     */
    @ApiOperation("deliver an order")
    @PutMapping("/delivery/{id}")
    public Result orderDelivery(@PathVariable Long id) {
        log.info("orderId:{}", id);
        orderService.deliverOrder(id);
        return Result.success();
    }

    /**
     * complete an order
     * @param id id
     * @return result
     */
    @ApiOperation("complete an order")
    @PutMapping("/complete/{id}")
    public Result orderComplete(@PathVariable Long id) {
        log.info("orderId:{}", id);
        orderService.completeOrder(id);
        return Result.success();
    }

    /**
     * view order statistics
     * @return order statistics
     */
    @ApiOperation("view order statistics")
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> orderStatistics() {
        log.info("view order statistics");
        OrderStatisticsVO orderStatisticsVO= orderService.orderStatistics();
        return Result.success(orderStatisticsVO);
    }



}
