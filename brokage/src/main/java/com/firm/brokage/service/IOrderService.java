package com.firm.brokage.service;

import com.firm.brokage.model.Order;
import com.firm.brokage.model.OrderHistoryRequest;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.model.OrderHistoryResponse;

import java.util.List;

public interface IOrderService {

    /* Finding Order list on repository **/
    OrderHistoryResponse getOrders(OrderHistoryRequest orderHistoryRequest);

    /* Finding Order on repository by id  and return Order model  */
    Order findOrderById(Long id);

    /* Saving Order on repository  and return OrderResponse model  **/
    GenericResponse saveOrder(Order order);

    /* deleting Order from repository  and return OrderResponse model  **/
    GenericResponse cancelOrder(Long id) throws Exception;

    /* matching order by admin  **/
    GenericResponse matchOrder(Long orderId) throws Exception;

}
