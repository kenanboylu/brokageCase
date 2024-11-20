package com.firm.brokage.service;

import com.firm.brokage.exception.ResourceNotFoundException;
import com.firm.brokage.model.Asset;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.repository.AssetRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AssetServiceTest {

    @InjectMocks
    AssetService assetService;

    @Mock
    AssetRepository assetRepository;

    Asset asset1;
    Asset asset2;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        asset1 = new Asset(200001L, 100001L, "DOGE", 1000L, 1000L);
        asset2 = new Asset(200002L, 100001L, "BTC", 2000L, 2000L);
    }

    @Test
    public void testCreateAssetThenReturnSuccess() {

        Mockito.when(assetRepository.saveAndFlush(Mockito.any(Asset.class))).thenReturn(asset1);

        GenericResponse response = assetService.createAsset(asset1);

        Assert.assertNotNull(response);
        Assert.assertEquals("000", response.getCode());
        Assert.assertEquals("Asset is saved successfully!", response.getMessage());
    }

    @Test
    public void testCreateAssetThenReturnThrowsException() {
        Mockito.when(assetRepository.saveAndFlush(Mockito.any(Asset.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> assetService.createAsset(asset2));
        Assert.assertEquals("Database error", exception.getMessage());

        Mockito.verify(assetRepository, Mockito.times(1)).saveAndFlush(asset2);
    }
}
