package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Order scheduled tasks
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * check and process time out order
     */
    @Scheduled(cron="0 * * * * ? ") //every minute
    public void processTimeoutOrder(){
        log.info("process timeout order:{}", LocalDateTime.now());

        List<Orders> ordersList =orderMapper.checkForTimeoutOrder(Orders.PENDING_PAYMENT,LocalDateTime.now().plusMinutes(-15));
        for(Orders orders:ordersList){
            orders.setStatus(Orders.CANCELLED);
            orders.setCancelReason("Order time out, has been cancelled automatically");
            orders.setCancelTime(LocalDateTime.now());
            orderMapper.modifyOrder(orders);
        }
    }

    /**
     * check and process the orders that are remain in deliver status
     */
    @Scheduled(cron = "0 0 1 * * ?") //1 o'clock in the morning, everyday
    public void processDeliveryOrder(){
        log.info("process delivery order:{}", LocalDateTime.now());

        List<Orders> ordersList =orderMapper.checkForTimeoutOrder(Orders.DELIVERY_IN_PROGRESS,LocalDateTime.now().plusMinutes(-60));
        for(Orders orders:ordersList){
            orders.setStatus(Orders.COMPLETED);
            orderMapper.modifyOrder(orders);
        }


    }
}
