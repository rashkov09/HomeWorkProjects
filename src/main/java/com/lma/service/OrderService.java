package com.lma.service;

public interface OrderService {

    void seedOrders();
    String createOrder(String clientName, String bookName);
    String getAllOrders();
    String getAllOrdersByClient(String clientName);
    String getAllOrdersIssuedOn(String date);
    String getAllOrdersIssuedAfter(String date);
    String getAllOrdersIssuedBefore(String date);

}
