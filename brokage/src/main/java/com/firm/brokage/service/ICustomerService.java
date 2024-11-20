package com.firm.brokage.service;

import com.firm.brokage.model.CustomerModel;
import com.firm.brokage.model.GenericResponse;

import java.util.List;

public interface ICustomerService {

    /* Finding Customer list on repository **/
    List<CustomerModel> getCustomerList();

    /* Finding Customer on repository by id  and return Customer model  */
    CustomerModel findCustomerById(Long id);

    /* Saving Customer on repository  and return CustomerResponse model  **/
    GenericResponse createCustomer(CustomerModel customer);

    /* updating Customer content and return CustomerResponse model **/
    GenericResponse updateCustomer(Long id, CustomerModel customer);

    /* deleting Customer from repository  and return CustomerResponse model  **/
    GenericResponse deleteCustomer(Long id) throws Exception;

}
