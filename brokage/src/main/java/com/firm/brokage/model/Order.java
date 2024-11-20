package com.firm.brokage.model;

import com.firm.brokage.enums.Side;
import com.firm.brokage.enums.Status;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "order")
public class Order implements Serializable {
    private Long orderId;
    private Long customerId;
    private String assetName;
    private Side side;
    private Long size;
    private Long price;
    private Status status;
    private Date createDate;
    private Date updateDate;

    public Order() {
    }

    public Order(Long orderId, Long customerId, Long assetId, String assetName, Side side, Long size, Long price, Status status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.assetName = assetName;
        this.side = side;
        this.size = size;
        this.price = price;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", sequenceName = "seq_brokage", allocationSize = 1, initialValue = 300001)
    @Column(name="order_id", nullable = false, updatable = false)
    public Long getOrderId() {
        return orderId;
    }

    @Column(name="customer_id", nullable = false, updatable = false)
    public Long getCustomerId() {
        return customerId;
    }

    @Column(name="asset_name", nullable = false, updatable = false)
    public String getAssetName() {
        return assetName;
    }

    @Column(name="side", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    public Side getSide() {
        return side;
    }

    @Column(name="size", nullable = false, updatable = false)
    public Long getSize() {
        return size;
    }

    @Column(name="price", nullable = false, updatable = false)
    public Long getPrice() {
        return price;
    }

    @Column(name="status", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    @Column(name="create_date", nullable = false, updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name="update_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
