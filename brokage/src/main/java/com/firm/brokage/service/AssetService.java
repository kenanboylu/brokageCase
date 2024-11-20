package com.firm.brokage.service;


import com.firm.brokage.exception.ResourceNotFoundException;
import com.firm.brokage.model.*;
import com.firm.brokage.repository.AssetRepository;
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
public class AssetService implements IAssetService{

    @Autowired
    AssetRepository assetRepository;


    @Override
    public AssetHistoryResponse getAssetList(AssetHistoryRequest request) {
        Page<Asset> assetList = null;
        Specification<Asset> assetSpecification = null;
        try {
            assetSpecification = createSpecification(Asset.class.newInstance(), request);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(request.getPage(), request.getPageCount(), Sort.by("createDate").descending());
        assetList = assetRepository.findAll(Specification.where(assetSpecification), pageable);


        AssetHistoryResponse assetHistoryResponse = new AssetHistoryResponse();
        assetHistoryResponse.setAssets(assetList.getContent());
        assetHistoryResponse.setTotal(assetList.getTotalElements());
        assetHistoryResponse.setPageCount(assetList.getTotalPages());

        return assetHistoryResponse;
    }

    @Override
    public Asset findAssetById(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);
        if(!asset.isPresent()) {
            throw new ResourceNotFoundException("Asset does not exist!" );
        }
        return asset.get();
    }

    @Override
    public Asset findAssetByNameAndCustomerId(String assetName, Long customerId) {
        Optional<Asset> asset = assetRepository.findAssetByNameAndCustomerId(assetName, customerId);
        if(!asset.isPresent()) {
            throw new ResourceNotFoundException( assetName + " asset not found");
        }
        return asset.get();
    }

    @Override
    public GenericResponse createAsset(Asset asset) {
        assetRepository.saveAndFlush(asset);
        return new GenericResponse("000", "Asset is saved successfully!");
    }

    @Override
    public GenericResponse updateAsset(Asset asset) {
        Optional<Asset> assetById = assetRepository.findAssetByNameAndCustomerId(asset.getAssetName(), asset.getCustomerId());
        if(!assetById.isPresent()) {
            throw new ResourceNotFoundException("Asset not found");
        }
        assetRepository.saveAndFlush(asset);
        return new GenericResponse("000", "Asset is updated successfully!");
    }

    @Override
    public GenericResponse deleteAsset(Long customerId, String assetName) throws Exception {
        Optional<Asset> asset = assetRepository.findAssetByNameAndCustomerId(assetName, customerId);
        if(!asset.isPresent()) {
            throw new ResourceNotFoundException("Asset not found");
        }
        assetRepository.deleteById(asset.get().getId());
        return new GenericResponse("000", "Asset is removed succesfully!");
    }

    public static <T> Specification<T> createSpecification(T type, AssetHistoryRequest assetHistoryRequest) {
        Specification<T> spsTxnSpecification = (asset, query, cb) -> {

            final Collection<Predicate> predicates = new ArrayList<>();
            Predicate p0 = cb.equal(asset.get("customerId"), assetHistoryRequest.getCustomerId());
            predicates.add(p0);

            if (assetHistoryRequest != null && !StringUtils.isEmpty(assetHistoryRequest.getSize())) {
                Predicate p1 = cb.equal(asset.get("side"), assetHistoryRequest.getSize());
                predicates.add(p1);
            }
            if (assetHistoryRequest != null && assetHistoryRequest.getUsableSize() != null) {
                Predicate p2 = cb.equal(asset.get("status"), assetHistoryRequest.getUsableSize());
                predicates.add(p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return spsTxnSpecification;

    }
}
