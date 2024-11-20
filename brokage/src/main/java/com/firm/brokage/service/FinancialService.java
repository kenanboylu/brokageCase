package com.firm.brokage.service;

import com.firm.brokage.enums.Side;
import com.firm.brokage.exception.ResourceNotFoundException;
import com.firm.brokage.model.Asset;
import com.firm.brokage.model.FinancialRequest;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinancialService implements IFinancialService {

    private static final String ASSET_TRY = "TRY";

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AssetService assetService;

    @Override
    public GenericResponse depositMoney(FinancialRequest financialRequest) {
        Asset assetTRY = null;
        Optional<Asset> asset = assetRepository.findAssetByNameAndCustomerId(ASSET_TRY, financialRequest.getCustomerId());
        if (asset.isPresent()) {
            assetTRY = asset.get();
            assetTRY.setSize(assetTRY.getSize() + financialRequest.getAmount());
            assetTRY.setUsableSize(assetTRY.getUsableSize() + financialRequest.getAmount());
            assetService.updateAsset(assetTRY);
        } else {
            assetTRY = new Asset();
            assetTRY.setCustomerId(financialRequest.getCustomerId());
            assetTRY.setAssetName(ASSET_TRY);
            assetTRY.setSize(financialRequest.getAmount());
            assetTRY.setUsableSize(financialRequest.getAmount());
            assetService.createAsset(assetTRY);
        }
        return new GenericResponse("000", "The operation is completed successfully!");
    }

    @Override
    public GenericResponse withdrawMoney(FinancialRequest financialRequest) {
        Asset assetTRY = null;
        Optional<Asset> asset = assetRepository.findAssetByNameAndCustomerId(ASSET_TRY, financialRequest.getCustomerId());
        if (asset.isPresent()) {
            assetTRY = asset.get();
            if (assetTRY.getUsableSize() < financialRequest.getAmount()) {
                throw new ResourceNotFoundException("You don't enough TRY to withdraw money");
            } else {
                assetTRY.setSize(assetTRY.getSize() - financialRequest.getAmount());
                assetTRY.setUsableSize(assetTRY.getUsableSize() - financialRequest.getAmount());
                assetService.updateAsset(assetTRY);
            }
        } else {
            throw new ResourceNotFoundException("You don't enough TRY to withdraw money");
        }
        return new GenericResponse("000", "The operation is completed successfully!");
    }
}
