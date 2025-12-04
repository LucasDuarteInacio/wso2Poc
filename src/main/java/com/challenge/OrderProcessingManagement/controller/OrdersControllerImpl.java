package com.challenge.OrderProcessingManagement.controller;

import com.challenge.OrderProcessingManagement.api.OrdersApi;
import com.challenge.OrderProcessingManagement.api.model.Order;
import com.challenge.OrderProcessingManagement.api.model.OrderInput;
import com.challenge.OrderProcessingManagement.api.model.UpdateOrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrdersControllerImpl implements OrdersApi {

    @Override
    public ResponseEntity<List<Order>> getAllOrders() {
        System.out.println("Listing Orders");
        // TODO: Implement logic to list all orders
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Order> createOrder(OrderInput orderInput) {
        System.out.println("Creating Order for Customer ID: " + orderInput.getCustomerId());
        // TODO: Implement logic to create a new order
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Order> getOrderById(Integer id) {
        System.out.println("Finding Order by ID: " + id);
        // TODO: Implement logic to find order by ID
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<Order>> getOrdersByCustomer(Integer customerId) {
        System.out.println("Listing Orders for Customer ID: " + customerId);
        // TODO: Implement logic to list orders by customer
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Order> updateOrderStatus(Integer id, UpdateOrderStatus updateOrderStatus) {
        System.out.println("Updating Order Status ID: " + id + " to: " + updateOrderStatus.getStatus());
        // TODO: Implement logic to update order status
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}

