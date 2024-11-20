package com.firm.brokage.model;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryResponse {

    private List<Order> orders;

    private long total;

    private int pageCount;

    public OrderHistoryResponse() {
        this.orders = new ArrayList<>();
    }

    public OrderHistoryResponse(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
