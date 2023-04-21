package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.OrderController;
import com.slm.springlibrarymanagement.controller.request.OrderRequest;
import com.slm.springlibrarymanagement.mappers.ClientMapper;
import com.slm.springlibrarymanagement.model.dto.OrderDto;
import com.slm.springlibrarymanagement.model.entities.Order;
import com.slm.springlibrarymanagement.service.ClientService;
import com.slm.springlibrarymanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Autowired
    public OrderControllerImpl(OrderService orderService, ClientService clientService, ClientMapper clientMapper) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrderByClientId(String id) {
        List<OrderDto> orderDto = orderService.findAllOrdersByClient(clientMapper.mapFromDto(clientService.findClientById(id)));
        return ResponseEntity.ok(orderDto);
    }

    @Override
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @Override
    public ResponseEntity<Void> createOrder(OrderRequest orderRequest) {
        Order order = orderService.insertOrder(orderRequest);

        URI location = UriComponentsBuilder.fromUriString("/order/{id}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
