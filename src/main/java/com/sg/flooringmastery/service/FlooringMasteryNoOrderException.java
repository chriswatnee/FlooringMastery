/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryNoOrderException extends Exception {
    
    public FlooringMasteryNoOrderException(String message) {
        super(message);
    }
    
    public FlooringMasteryNoOrderException(String message, Throwable cause) {
        super(message, cause);
    }

}
