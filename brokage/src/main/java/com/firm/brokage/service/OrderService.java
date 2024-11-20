package com.firm.brokage.service;

import com.firm.brokage.enums.Side;
import com.firm.brokage.enums.Status;
import com.firm.brokage.enums.Status;
import com.firm.brokage.exception.ResourceNotFoundException;
import com.firm.brokage.model.*;
import com.firm.brokage.repository.OrderRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class OrderService implements IOrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    IAssetService assetService;

    private static final String ASSET_TRY = "TRY";


    @Override
    public OrderHistoryResponse getOrders(OrderHistoryRequest request) {
        Page<Order> orderList = null;
        Specification<Order> orderSpecification = null;
        try {
            orderSpecification = createSpecification(Order.class.newInstance(), request);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getPageCount(), Sort.by("createDate").descending());
        orderList = orderRepository.findAll(Specification.where(orderSpecification), pageable);


        OrderHistoryResponse orderHistoryResponse = new OrderHistoryResponse();
        orderHistoryResponse.setOrders(orderList.getContent());
        orderHistoryResponse.setTotal(orderList.getTotalElements());
        orderHistoryResponse.setPageCount(orderList.getTotalPages());

        return orderHistoryResponse;
    }

    @Override
    public Order findOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()) {
            throw new ResourceNotFoundException("Order does not exist!" );
        }
        return order.get();
    }

    @Override
    public GenericResponse saveOrder(Order order) {
        if(order != null)
            order.setCreateDate(new Date());

        Asset assetTRY = assetService.findAssetByNameAndCustomerId(ASSET_TRY, order.getCustomerId());

        if(Side.BUY.equals(order.getSide()) && assetTRY.getUsableSize() < order.getSize())
            throw new ResourceNotFoundException("You don't enough TRY to buy asset");

        order.setStatus(Status.PENDING);
        orderRepository.saveAndFlush(order);

        if(Side.BUY.equals(order.getSide())) {
            assetTRY.setUsableSize(assetTRY.getUsableSize() - order.getSize());
        }else if(Side.SELL.equals(order.getSide())) {
            assetTRY.setUsableSize(assetTRY.getUsableSize() + order.getSize());
        }
        assetService.updateAsset(assetTRY);

        return new GenericResponse("000", "Order is saved successfully!");
    }

    @Override
    public GenericResponse cancelOrder(Long id) throws Exception {
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()) {
            throw new ResourceNotFoundException("Order not found by id " + id);
        }
        if(!Status.PENDING.equals(order.get().getStatus()))
            throw new Exception("You are not able to delete order");

        Asset assetTRY = assetService.findAssetByNameAndCustomerId(ASSET_TRY, id);

        order.get().setStatus(Status.CANCELED);
        orderRepository.saveAndFlush(order.get());

        if(Side.BUY.equals(order.get().getSide())) {
            assetTRY.setUsableSize(assetTRY.getUsableSize() + order.get().getSize());
        }else if(Side.SELL.equals(order.get().getSide())) {
            assetTRY.setUsableSize(assetTRY.getUsableSize() - order.get().getSize());
        }
        assetService.updateAsset(assetTRY);

        return new GenericResponse("000", "Order is cancelled succesfully!");
    }

    @Override
    public GenericResponse matchOrder(Long orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if(!order.isPresent()) {
            throw new ResourceNotFoundException("Order not found by id " + orderId);
        }

        if(!Status.PENDING.equals(order.get().getStatus()))
            throw new Exception("Order status is not valid");

        Asset asset = assetService.findAssetByNameAndCustomerId(order.get().getAssetName(), order.get().getCustomerId());
        if (asset == null)
            throw new ResourceNotFoundException("Asset is not found");

        order.get().setStatus(Status.MATCHED);
        orderRepository.saveAndFlush(order.get());
        return new GenericResponse("000", "Order is matched successfully!");
    }

    public static <T> Specification<T> createSpecification(T type, OrderHistoryRequest orderHistoryRequest) {
        Specification<T> spsTxnSpecification = (order, query, cb) -> {

            final Collection<Predicate> predicates = new ArrayList<>();
            Predicate p0 = cb.equal(order.get("customerId"), orderHistoryRequest.getCustomerId());
            predicates.add(p0);

            if (orderHistoryRequest != null && !StringUtils.isEmpty(orderHistoryRequest.getSide())) {
                Predicate p1 = cb.equal(order.get("side"), orderHistoryRequest.getSide());
                predicates.add(p1);
            }
            if (orderHistoryRequest != null && orderHistoryRequest.getStatus() != null) {
                Predicate p2 = cb.equal(order.get("status"), orderHistoryRequest.getStatus());
                predicates.add(p2);
            }
            if (orderHistoryRequest != null && orderHistoryRequest.getStartDate() != null) {
                Predicate p8 = cb.greaterThanOrEqualTo(order.get("createDate"), orderHistoryRequest.getStartDate());
                predicates.add(p8);
            }
            if (orderHistoryRequest != null && orderHistoryRequest.getEndDate() != null) {
                Predicate p8 = cb.lessThanOrEqualTo(order.get("createDate"), orderHistoryRequest.getEndDate());
                predicates.add(p8);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return spsTxnSpecification;

    }

}
