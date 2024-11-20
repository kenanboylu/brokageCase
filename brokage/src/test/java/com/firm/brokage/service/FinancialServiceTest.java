package com.firm.brokage.service;

import com.firm.brokage.model.Asset;
import com.firm.brokage.model.FinancialRequest;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.repository.AssetRepository;
import com.firm.brokage.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FinancialServiceTest {

    @InjectMocks
    FinancialService financialService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    AssetRepository assetRepository;

    Asset asset1;
    Asset asset2;


    @Test
    public void testDepositMoneyThenReturnSuccess() {
        FinancialRequest financialRequest = new FinancialRequest();
        financialRequest.setAmount(1000L); // Set the amount or other properties as required
        financialRequest.setCustomerId(100001L);

        GenericResponse response = financialService.depositMoney(financialRequest);

        Assert.assertNotNull(response);
        Assert.assertEquals("000", response.getCode());
        Assert.assertEquals("The operation is completed successfully!", response.getMessage());
    }

    @Test
    public void testDepositMoney_emptyRequest() {
        GenericResponse response = financialService.depositMoney(null);
        Assert.assertNotNull(response);
        Assert.assertEquals("000", response.getCode());
        Assert.assertEquals("The operation is completed successfully!", response.getMessage());
    }
}
