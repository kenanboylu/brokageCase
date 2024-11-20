package com.firm.brokage.service;

import com.firm.brokage.exception.ResourceNotFoundException;
import com.firm.brokage.model.CustomerModel;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<CustomerModel> getCustomerList() {
        List<CustomerModel> customerList = customerRepository.findAll();
        if(CollectionUtils.isEmpty(customerList)) {
            throw new ResourceNotFoundException("Customer does not exist!" );
        }
        return customerList;
    }

    @Override
    public CustomerModel findCustomerById(Long id) {
        Optional<CustomerModel> customer = customerRepository.findById(id);
        if(!customer.isPresent()) {
            throw new ResourceNotFoundException("Customer does not exist!" );
        }
        return customer.get();
    }

    @Override
    public GenericResponse createCustomer(CustomerModel customer) {
        customerRepository.saveAndFlush(customer);
        return new GenericResponse("000", "Customer is saved successfully!");
    }

    @Override
    public GenericResponse updateCustomer(Long id, CustomerModel customer) {
        Optional<CustomerModel> customerById = customerRepository.findById(id);
        if(!customerById.isPresent()) {
            throw new ResourceNotFoundException("Customer not found by id " + id);
        }
        customerRepository.saveAndFlush(customer);
        return new GenericResponse("000", "Customer is updated successfully!");
    }

    @Override
    public GenericResponse deleteCustomer(Long id) throws Exception {
        Optional<CustomerModel> customer = customerRepository.findById(id);
        if(!customer.isPresent()) {
            throw new ResourceNotFoundException("Customer not found by id " + id);
        }

        customerRepository.deleteById(id);
        return new GenericResponse("000", "Customer is removed succesfully!");
    }
}
