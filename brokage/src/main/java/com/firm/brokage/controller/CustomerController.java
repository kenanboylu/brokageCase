package com.firm.brokage.controller;

import com.firm.brokage.model.CustomerModel;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    ICustomerService customerService;


    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomer( @RequestBody CustomerModel customer)  throws Throwable {
        GenericResponse orderResponse = customerService.createCustomer(customer);
        return new ResponseEntity<GenericResponse>(orderResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer( @RequestParam(name = "customerId") Long customerId, @RequestBody CustomerModel customer)  throws Throwable {
        GenericResponse orderResponse = customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<GenericResponse>(orderResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCustomer( @RequestParam(name = "customerId") Long customerId)  throws Throwable {
        GenericResponse orderResponse = customerService.deleteCustomer(customerId);
        return new ResponseEntity<GenericResponse>(orderResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAllCustomers()  throws Throwable {
        List<CustomerModel> orderList = customerService.getCustomerList();
        return new ResponseEntity<List>(orderList, HttpStatus.OK);
    }

}
