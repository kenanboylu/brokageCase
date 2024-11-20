package com.firm.brokage.controller;

import com.firm.brokage.model.Order;
import com.firm.brokage.model.OrderHistoryRequest;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.model.OrderHistoryResponse;
import com.firm.brokage.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService orderService;


    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder( @RequestBody Order order)  throws Throwable {
        GenericResponse orderResponse = orderService.saveOrder(order);
        return new ResponseEntity<GenericResponse>(orderResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteOrder( @RequestParam(name = "orderId") Long orderId)  throws Throwable {
        GenericResponse orderResponse = orderService.cancelOrder(orderId);
        return new ResponseEntity<GenericResponse>(orderResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderHistoryResponse getOrders(@RequestBody OrderHistoryRequest orderHistoryRequest)  throws Throwable {
        OrderHistoryResponse response = orderService.getOrders(orderHistoryRequest);
        return response;
    }

}
