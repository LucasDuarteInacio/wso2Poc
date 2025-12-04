package com.challenge.OrderProcessingManagement.service.impl;

import com.challenge.OrderProcessingManagement.api.model.Customer;
import com.challenge.OrderProcessingManagement.mapper.CustomerMapper;
import com.challenge.OrderProcessingManagement.model.CustomerModel;
import com.challenge.OrderProcessingManagement.repository.CustomerRepository;
import com.challenge.OrderProcessingManagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        List<CustomerModel> customerModels = customerRepository.findAll();
        return customerMapper.toCustomer(customerModels);
    }
}
