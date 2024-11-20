package com.firm.brokage.controller;

import com.firm.brokage.model.Asset;
import com.firm.brokage.model.GenericResponse;
import com.firm.brokage.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IOrderService orderService;

    @RequestMapping(value = "/matchOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> matchOrder(@RequestParam(name = "orderId") Long orderId)  throws Throwable {
        GenericResponse response = orderService.matchOrder(orderId);
        return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
    }
}
