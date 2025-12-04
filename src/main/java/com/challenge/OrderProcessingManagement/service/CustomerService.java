package com.challenge.OrderProcessingManagement.service;

import com.challenge.OrderProcessingManagement.api.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomers();

}
