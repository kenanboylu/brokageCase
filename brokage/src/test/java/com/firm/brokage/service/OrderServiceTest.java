package com.firm.brokage.service;

import com.firm.brokage.enums.Side;
import com.firm.brokage.enums.Status;
import com.firm.brokage.exception.ResourceNotFoundException;
import com.firm.brokage.model.*;
import com.firm.brokage.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    Long CONS_CUSTOMER_ID = 100001L;

    @Mock
    OrderRepository orderRepository;
    @Mock
    AssetService assetService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private Order order1;
    private Order order2;
    private Order order3;
    private OrderHistoryRequest request;
    private Page<Order> orderPage;

    private Asset assetTRY;
    private static final String ASSET_TRY = "TRY";

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        order1 = new Order(300001L,CONS_CUSTOMER_ID,200001L,"DOGE", Side.BUY,10L, 4L, Status.PENDING);
        order2 = new Order(300002L,CONS_CUSTOMER_ID,200002L,"BTC", Side.BUY, 1L, 92L, Status.MATCHED);
        order3 = new Order(300002L,CONS_CUSTOMER_ID,200003L,"BONK", Side.SELL,10L, 1L, Status.CANCELED);

        request = new OrderHistoryRequest();
        request.setPage(0);
        request.setPageCount(10);

        List<Order> orders = Arrays.asList(order1, order2);
        orderPage = new PageImpl<>(orders, PageRequest.of(request.getPage(), request.getPageCount()), orders.size());

        assetTRY = new Asset(200001L, 100001L, "TRY", 1000L, 1000L);

    }


    @Test
    public void testGetOrdersWithValidRequestTheRetunSuccessResult() {
        Mockito.when(orderRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(orderPage);

        OrderHistoryResponse response = orderService.getOrders(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(2, response.getOrders().size());
        Assert.assertEquals(2, response.getTotal());
        Assert.assertEquals(1, response.getPageCount());
    }

    @Test
    public void testGetOrdersWithValidRequestTheRetunEmptyResult() {
        Page<Order> emptyOrderPage = new PageImpl<>(Arrays.asList(), PageRequest.of(request.getPage(), request.getPageCount()), 0);
        Mockito.when(orderRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(emptyOrderPage);
        OrderHistoryResponse response = orderService.getOrders(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(0, response.getOrders().size());
        Assert.assertEquals(0, response.getTotal());
        Assert.assertEquals(0, response.getPageCount());
    }

    @Test
    public void testSaveOrderWithSuccessfulBuyOrder() {
        Mockito.when(assetService.findAssetByNameAndCustomerId(ASSET_TRY, order1.getCustomerId())).thenReturn(assetTRY);
        Mockito.when(orderRepository.saveAndFlush(Mockito.any(Order.class))).thenReturn(order1);

        GenericResponse response = orderService.saveOrder(order1);

        Assert.assertNotNull(response);
        Assert.assertEquals("000", response.getCode());
        Assert.assertEquals("Order is saved successfully!", response.getMessage());

        Assert.assertNotNull(order1.getCreateDate());
        Assert.assertEquals(Status.PENDING, order1.getStatus());
    }

    @Test
    public void testSaveOrderWithSuccessfulSellOrder() {
        Mockito.when(assetService.findAssetByNameAndCustomerId(ASSET_TRY, order2.getCustomerId())).thenReturn(assetTRY);
        Mockito.when(orderRepository.saveAndFlush(Mockito.any(Order.class))).thenReturn(order2);

        GenericResponse response = orderService.saveOrder(order2);

        Assert.assertNotNull(response);
        Assert.assertEquals("000", response.getCode());
        Assert.assertEquals("Order is saved successfully!", response.getMessage());
    }

    @Test
    public void testSaveOrderWithInsufficientTRYForBuyOrder() {
        assetTRY.setUsableSize(5L);
        Mockito.when(assetService.findAssetByNameAndCustomerId(ASSET_TRY, order1.getCustomerId())).thenReturn(assetTRY);
        ResourceNotFoundException exception = Assert.assertThrows(ResourceNotFoundException.class, () -> orderService.saveOrder(order1));
        Assert.assertEquals("You don't enough TRY to buy asset", exception.getMessage());
    }

    @Test
    public void testMatchOrderWithIdNotFoundThrowsException() {
        ResourceNotFoundException exception = Assert.assertThrows(ResourceNotFoundException.class, () -> {
            orderService.matchOrder(300001L);
        });

        Assert.assertEquals("Order not found by id " + 300001L, exception.getMessage());
    }

    @Test
    public void testMatchOrderWithInvalidStatusThrowsException() {
        Mockito.when(orderRepository.findById(300001L)).thenReturn(Optional.ofNullable(order2));

        Exception exception = Assert.assertThrows(Exception.class, () -> {
            orderService.matchOrder(300001L);
        });

        Assert.assertEquals("Order status is not valid", exception.getMessage());
    }
}
