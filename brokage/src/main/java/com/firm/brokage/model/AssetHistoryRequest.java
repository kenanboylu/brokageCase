package com.firm.brokage.model;

import java.util.Date;

public class AssetHistoryRequest {

    private Long customerId;
    private Integer size;
    private Integer usableSize;
    private Integer page = 0;
    private Integer pageCount = 100;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getUsableSize() {
        return usableSize;
    }

    public void setUsableSize(Integer usableSize) {
        this.usableSize = usableSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
