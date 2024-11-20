package com.firm.brokage.service;

import com.firm.brokage.model.FinancialRequest;
import com.firm.brokage.model.GenericResponse;

public interface IFinancialService {

    public GenericResponse depositMoney(FinancialRequest financialRequest);

    public GenericResponse  withdrawMoney(FinancialRequest financialRequest);
}
