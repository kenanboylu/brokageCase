package com.firm.brokage.repository;

import com.firm.brokage.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {

    @Query("select t from Asset t where t.customerId = :customerId")
    List<Asset> findAssetByCustomerId(@Param("customerId") Long customerId);

    @Query("select t from Asset t where t.assetName = :assetName and t.customerId = :customerId")
    Optional<Asset> findAssetByNameAndCustomerId(@Param("assetName") String assetName, @Param("customerId") Long customerId);

    @Query("select t from Asset t where t.customerId = :customerId and t.size = :size")
    List<Asset> findAssetByCustIdAndSize(@Param("customerId") Long customerId, @Param("size") Integer size);

    @Query("select t from Asset t where t.customerId = :customerId and t.usableSize = :usableSize")
    List<Asset> findAssetByCustIdAndUsableSize(@Param("customerId") Long customerId, @Param("usableSize") Integer usableSize);

}
