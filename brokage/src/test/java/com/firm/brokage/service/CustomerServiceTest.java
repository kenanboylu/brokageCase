package com.firm.brokage.service;

import com.firm.brokage.exception.ResourceNotFoundException;
import com.firm.brokage.model.Asset;
import com.firm.brokage.model.CustomerModel;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.repository.CustomerRepository;
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
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    CustomerModel customer1;
    CustomerModel customer2;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer1 = new CustomerModel(100001L, "Ciro", "Immobile", null);
        customer2 = new CustomerModel(100002L, "Rafa", "Silva", null);
    }

    @Test
    public void testGetCustomerListWithAssetIdNotFoundThenReturnException() {
        ResourceNotFoundException exception = Assert.assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getCustomerList();
        });

        Assert.assertEquals("Customer does not exist!", exception.getMessage());
    }

    @Test
    public void testCreateCustomerThenReturnSuccess() {

        Mockito.when(customerRepository.saveAndFlush(Mockito.any(CustomerModel.class))).thenReturn(customer1);

        GenericResponse response = customerService.createCustomer(customer1);

        Assert.assertNotNull(response);
        Assert.assertEquals("000", response.getCode());
        Assert.assertEquals("Customer is saved successfully!", response.getMessage());
    }

    @Test
    public void testCreateCustomerThenReturnThrowsException() {
        Mockito.when(customerRepository.saveAndFlush(Mockito.any(CustomerModel.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> customerService.createCustomer(customer2));
        Assert.assertEquals("Database error", exception.getMessage());

        Mockito.verify(customerRepository, Mockito.times(1)).saveAndFlush(customer2);
    }
}
