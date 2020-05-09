/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;

/**
 *
 * @author christopherwatnee
 */
public interface FlooringMasteryTaxesDao {
    
    void loadTaxes() throws FlooringMasteryPersistenceException;
    
    Tax addTax(String state, Tax tax);
    
    Tax getTax(String state);
    
}
