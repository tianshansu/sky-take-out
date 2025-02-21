package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "user order related")
public class OderController {

    @Autowired
    private OrderService orderService;

    /**
     * user places order
     *
     * @param ordersSubmitDTO ordersSubmitDTO
     * @return result-order submit VO
     */
    @ApiOperation("user places order")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("orderSubmitDTO:{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }


    /**
     * order history
     * @param page page
     * @param pageSize pageSize
     * @param status order status
     * @return page result
     */
    @ApiOperation("order history")
    @GetMapping("/historyOrders")
    public Result<PageResult> historyOrders(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) Integer status) {
        log.info("page:{},pageSize:{},status:{}", page, pageSize, status);
        PageResult pageResult=orderService.page(page,pageSize,status);
        return Result.success(pageResult);

    }

    /**
     * view a single order detail
     * @param id order id
     * @return orderVO
     */
    @ApiOperation("order detail")
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> orderDetail(@PathVariable Long id) {
        log.info("orderDetail:{}", id);
        OrderVO orderVO=orderService.orderDetail(id);
        return Result.success(orderVO);
    }

    /**
     * cancel an order
     * @param id order id
     * @return result
     */
    @ApiOperation("cancel an order")
    @PutMapping("/cancel/{id}")
    public Result cancelOrder(@PathVariable Long id) {
        log.info("cancelOrder:{}", id);
        orderService.cancelOrder(id);
        return Result.success();
    }

    /**
     * place the same order again
     * @param id previous order's id
     * @return result
     */
    @ApiOperation("place the same order again")
    @PostMapping("/repetition/{id}")
    public Result orderRepetition(@PathVariable Long id) {
        log.info("orderRepetition:{}", id);
        orderService.orderRepetition(id);
        return Result.success();
    }



}
