package com.firm.brokage.controller;

import com.firm.brokage.model.FinancialRequest;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.model.Order;
import com.firm.brokage.service.IFinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financial")
public class FinancialController {

    @Autowired
    IFinancialService financialService;

    @RequestMapping(value = "/deposit/money", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> depositMoney(@RequestBody FinancialRequest financialRequest)  throws Throwable {
        GenericResponse response = financialService.depositMoney(financialRequest);
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/withdraw/money", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdrawMoney(@RequestBody FinancialRequest financialRequest)  throws Throwable {
        GenericResponse response = financialService.depositMoney(financialRequest);
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }

}
