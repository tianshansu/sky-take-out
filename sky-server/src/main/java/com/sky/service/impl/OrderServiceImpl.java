package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private WebSocketServer webSocketServer;



    /**
     * user places order
     *
     * @param ordersSubmitDTO ordersSubmitDTO
     * @return order submit VO
     */
    @Transactional
    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {

        //get address info from address table
        AddressBook addressBook = addressBookMapper.getAddressById(ordersSubmitDTO.getAddressBookId());

        //get element inside shopping cart(to know what has the user ordered-to insert to order details table)
        ShoppingCart shoppingCart = ShoppingCart.builder().userId(BaseContext.getCurrentId()).build();
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.list(shoppingCart);

        //handle exceptions(prevent attacks bypassing the frontend)
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }


        Orders order = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, order);
        order.setUserId(BaseContext.getCurrentId());
        order.setNumber(String.valueOf(System.currentTimeMillis()));
        order.setStatus(Orders.PENDING_PAYMENT);
        order.setOrderTime(LocalDateTime.now());
        order.setPayStatus(Orders.UN_PAID);
        order.setPhone(addressBook.getPhone());
        order.setAddress(addressBook.getDetail());
        order.setConsignee(addressBook.getConsignee());

        //insert this order into orders table
        orderMapper.submit(order);

        //insert info into order detail table
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart ele : shoppingCarts) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(ele, orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetails.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetails);

        //clear shopping cart
        shoppingCartMapper.clean(BaseContext.getCurrentId());

        //create orderSubmitVO to return to frontend
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        orderSubmitVO.setOrderTime(order.getOrderTime());
        orderSubmitVO.setOrderAmount(order.getAmount());
        orderSubmitVO.setId(order.getId());
        orderSubmitVO.setOrderNumber(order.getNumber());

        return orderSubmitVO;
    }

    /**
     * order history
     *
     * @param page     page
     * @param pageSize pageSize
     * @param status   order status
     * @return page result
     */
    @Override
    public PageResult page(Integer page, Integer pageSize, Integer status) {
        //start paging - use PageHelper plugin
        PageHelper.startPage(page, pageSize);

        //get all orders of current user
        Page<Orders> ordersList = orderMapper.page(Orders.builder().status(status).userId(BaseContext.getCurrentId()).build());

        //construct ordersVOs
        return getPageResult(ordersList);

    }

    /**
     * view a single order detail
     *
     * @param id order id
     * @return orderVO
     */
    @Override
    public OrderVO orderDetail(Long id) {
        Orders order = orderMapper.findOrderById(id);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        List<OrderDetail> orderDetails = orderDetailMapper.findDetailsByOrderId(order.getId());
        orderVO.setOrderDetailList(orderDetails);

        return orderVO;
    }

    /**
     * cancel an order
     *
     * @param ordersCancelDTO order id
     */
    @Override
    public void cancelOrder(OrdersCancelDTO ordersCancelDTO) {
        Orders order=new Orders();
        BeanUtils.copyProperties(ordersCancelDTO, order);
        //change order status to [cancelled]
        order.setStatus(Orders.CANCELLED);
        order.setCancelTime(LocalDateTime.now());
        orderMapper.modifyOrder(order);
    }

    /**
     * place the same order again
     *
     * @param id previous order's id
     */
    @Transactional
    @Override
    public void orderRepetition(Long id) {
        //find info of the previous order
        Orders orderPrev = orderMapper.findOrderById(id);
        List<OrderDetail> orderDetailList = orderDetailMapper.findDetailsByOrderId(id);

        Orders orderNew = new Orders();
        BeanUtils.copyProperties(orderPrev, orderNew);
        orderNew.setId(null);
        orderNew.setOrderTime(LocalDateTime.now());
        orderNew.setRejectionReason(null);
        orderNew.setEstimatedDeliveryTime(null);
        orderNew.setCheckoutTime(null);
        orderNew.setPayStatus(Orders.UN_PAID);
        orderNew.setCancelReason(null);
        orderNew.setDeliveryTime(null);
        orderNew.setCancelTime(null);
        orderNew.setStatus(Orders.PENDING_PAYMENT);
        orderNew.setNumber(String.valueOf(System.currentTimeMillis()));

        orderMapper.submit(orderNew);

        //change order id in detail list to the new order id
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetail.setOrderId(orderNew.getId());
        }
        orderDetailMapper.insertBatch(orderDetailList);

    }

    /**
     * orders conditional search
     *
     * @param ordersPageQueryDTO ordersPageQueryDTO
     * @return page result
     */
    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        //start paging - use PageHelper plugin
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        Page<Orders> orders = orderMapper.conditionSearch(ordersPageQueryDTO);

        return getPageResult(orders);
    }

    /**
     * confirm an order
     *
     * @param ordersConfirmDTO ordersDTO
     */
    @Override
    public void acceptOrder(OrdersConfirmDTO ordersConfirmDTO) {
        Orders order=new Orders();
        BeanUtils.copyProperties(ordersConfirmDTO, order);

        //change order status to [confirmed]
        order.setStatus(Orders.CONFIRMED);
        orderMapper.modifyOrder(order);
    }

    /**
     * reject an order
     * @param ordersRejectionDTO ordersRejectionDTO
     */
    @Override
    public void rejectOrder(OrdersRejectionDTO ordersRejectionDTO) {
        Orders order=new Orders();
        BeanUtils.copyProperties(ordersRejectionDTO, order);

        //change order status to [cancelled]
        order.setStatus(Orders.CANCELLED);
        orderMapper.modifyOrder(order);
    }

    /**
     * deliver an order
     * @param orderId orderId
     */
    @Override
    public void deliverOrder(Long orderId) {
        Orders order = Orders.builder().id(orderId).status(Orders.DELIVERY_IN_PROGRESS).build();
        orderMapper.modifyOrder(order);
    }

    /**
     * complete an order
     * @param orderId orderId
     */
    @Override
    public void completeOrder(Long orderId) {
        Orders order = Orders.builder().id(orderId).status(Orders.COMPLETED).build();
        orderMapper.modifyOrder(order);
    }

    /**
     * view order statistics
     * @return order statistics VO
     */
    @Override
    public OrderStatisticsVO orderStatistics() {
        Integer confirmed=orderMapper.statistics(Orders.CONFIRMED);
        Integer deliveryInProgress=orderMapper.statistics(Orders.DELIVERY_IN_PROGRESS);
        Integer toBeConfirmed=orderMapper.statistics(Orders.TO_BE_CONFIRMED);

        OrderStatisticsVO orderStatisticsVO=new OrderStatisticsVO();
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);

        return orderStatisticsVO;
    }

    /**
     * order payment (not actual Wechat payment)
     * @param ordersPaymentDTO ordersPaymentDTO
     * @return orderPaymentVO
     */
    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) {
        Long userId=BaseContext.getCurrentId();

        JSONObject jsonObject=new JSONObject();

        if(jsonObject.getString("code")!=null && jsonObject.getString("code").equals("ORDERPAID")){
            throw new OrderBusinessException("This order has been payed.");
        }

        OrderPaymentVO orderPaymentVO=jsonObject.toJavaObject(OrderPaymentVO.class);
        orderPaymentVO.setPackageStr(jsonObject.getString("package"));

        return orderPaymentVO;
    }

    /**
     * payment success
     * @param orderNum order number
     */
    @Override
    public void paySuccess(String orderNum){
        Orders orderDB=orderMapper.findOrderByNumber(orderNum);

        Orders order=new Orders();
        order.setId(orderDB.getId());
        order.setStatus(Orders.TO_BE_CONFIRMED);
        order.setPayStatus(Orders.PAID);
        order.setCheckoutTime(LocalDateTime.now());

        orderMapper.modifyOrder(order);

        Map map=new HashMap<>();
        map.put("type",1);
        map.put("orderId",orderDB.getId());
        map.put("content","Order Number:"+orderNum);

        String json= JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);

    }

    /**
     * order reminder
     * @param id order id
     */
    @Override
    public void reminder(Long id) {
        Orders order=orderMapper.findOrderById(id);

        Map map=new HashMap<>();
        map.put("type",2);
        map.put("orderId",order.getId());
        map.put("content","Order Number:"+order.getNumber());

        String json= JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);
    }

    private PageResult getPageResult(Page<Orders> orders) {
        List<OrderVO> orderVOs = new ArrayList<>();
        for (Orders order : orders) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            List<OrderDetail> orderDetails = orderDetailMapper.findDetailsByOrderId(order.getId());
            orderVO.setOrderDetailList(orderDetails);
            orderVOs.add(orderVO);
        }
        return new PageResult(orders.getTotal(), orderVOs);
    }

}
