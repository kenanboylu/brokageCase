package com.firm.brokage.service;

import com.firm.brokage.model.AssetHistoryResponse;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.model.Asset;
import com.firm.brokage.model.AssetHistoryRequest;

import java.util.List;

public interface IAssetService {

    /* Finding Asset list on repository **/
    AssetHistoryResponse getAssetList(AssetHistoryRequest AssetHistoryRequest);

    /* Finding Asset on repository by id  and return Asset model  */
    Asset findAssetById(Long id);

    /* Finding Asset on repository by id  and return Asset model  */
    Asset findAssetByNameAndCustomerId(String assetName, Long customerId);

    /* Saving Asset on repository  and return AssetResponse model  **/
    GenericResponse createAsset(Asset asset);

    /* updating Asset content and return AssetResponse model **/
    GenericResponse updateAsset(Asset asset);

    /* deleting Asset from repository  and return AssetResponse model  **/
    GenericResponse deleteAsset(Long customerId, String assetName) throws Exception;

}
