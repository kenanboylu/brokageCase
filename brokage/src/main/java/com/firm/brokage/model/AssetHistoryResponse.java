package com.firm.brokage.model;

import java.util.ArrayList;
import java.util.List;

public class AssetHistoryResponse {

    private List<Asset> assets;

    private long total;

    private int pageCount;

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
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
