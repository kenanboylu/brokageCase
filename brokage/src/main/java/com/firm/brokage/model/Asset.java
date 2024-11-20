package com.firm.brokage.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "asset")
public class Asset implements Serializable {
    private Long id;
    private Long customerId;
    private String assetName;
    private Long size;
    private Long usableSize;


    public Asset() {
    }

    public Asset(Long id, Long customerId, String assetName, Long size, Long usableSize) {
        this.id = id;
        this.customerId = customerId;
        this.assetName = assetName;
        this.size = size;
        this.usableSize = usableSize;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", sequenceName = "seq_asset", allocationSize = 1, initialValue = 200001)
    @Column(name="id", nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    @Column(name="customer_id", nullable = false, updatable = false)
    public Long getCustomerId() {
        return customerId;
    }

    @Column(name="asset_name", nullable = false, updatable = false)
    public String getAssetName() {
        return assetName;
    }

    @Column(name="size", nullable = false, updatable = false)
    public Long getSize() {
        return size;
    }

    @Column(name="usable_size", nullable = false, updatable = false)
    public Long getUsableSize() {
        return usableSize;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setUsableSize(Long usableSize) {
        this.usableSize = usableSize;
    }
}
