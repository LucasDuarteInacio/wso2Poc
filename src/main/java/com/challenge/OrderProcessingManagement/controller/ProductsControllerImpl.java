package com.challenge.OrderProcessingManagement.controller;

import com.challenge.OrderProcessingManagement.api.ProductsApi;
import com.challenge.OrderProcessingManagement.api.model.Product;
import com.challenge.OrderProcessingManagement.api.model.ProductInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductsControllerImpl implements ProductsApi {

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        System.out.println("Listing Products");
        // TODO: Implement logic to list all products
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Product> createProduct(ProductInput productInput) {
        System.out.println("Creating Product: " + productInput.getName());
        // TODO: Implement logic to create a new product
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Product> getProductById(Integer id) {
        System.out.println("Finding Product by ID: " + id);
        // TODO: Implement logic to find product by ID
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Product> updateProduct(Integer id, ProductInput productInput) {
        System.out.println("Updating Product ID: " + id);
        // TODO: Implement logic to update product
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer id) {
        System.out.println("Deleting Product ID: " + id);
        // TODO: Implement logic to delete product
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}

