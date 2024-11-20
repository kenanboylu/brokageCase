package com.firm.brokage.controller;

import com.firm.brokage.model.Asset;
import com.firm.brokage.model.AssetHistoryRequest;
import com.firm.brokage.model.AssetHistoryResponse;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.service.IAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    IAssetService assetService;


    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAsset( @RequestBody Asset asset)  throws Throwable {
        GenericResponse response = assetService.createAsset(asset);
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAsset(@RequestBody Asset asset)  throws Throwable {
        GenericResponse response = assetService.updateAsset(asset);
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAsset( @RequestParam(name = "customerId") Long customerId, String assetName)  throws Throwable {
        GenericResponse assetResponse = assetService.deleteAsset(customerId, assetName);
        return new ResponseEntity<GenericResponse>(assetResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/assets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AssetHistoryResponse getAssets(@RequestBody AssetHistoryRequest assetHistoryRequest)  throws Throwable {
        AssetHistoryResponse response = assetService.getAssetList(assetHistoryRequest);
        return response;
    }

}
