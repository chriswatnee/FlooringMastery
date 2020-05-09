/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;

/**
 *
 * @author christopherwatnee
 */
public interface FlooringMasteryProductsDao {
    
    void loadProducts() throws FlooringMasteryPersistenceException;
    
    Product addProduct(String productType, Product product);
    
    Product getProduct(String productType);
    
}