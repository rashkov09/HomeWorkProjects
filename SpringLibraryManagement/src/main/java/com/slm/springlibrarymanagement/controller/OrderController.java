package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.model.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderController {

    @GetMapping("/orders/{id}")
    ResponseEntity<List<OrderDto>> getOrderByClientId(@PathVariable
                                            String id);

    @GetMapping("/orders")
    ResponseEntity<List<OrderDto>> getAllOrders();
}
