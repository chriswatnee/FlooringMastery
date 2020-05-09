/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryView {
    
    private UserIO io;
    
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("*  <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Save Current Work");
        io.print("* 6. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    public void displayReturnToMainMenu() {
        io.readString("Please hit enter to return to main menu.");
    }
    
    public void displayDisplayOrdersBanner() {
        io.print("*** Display Orders ***");
    }
    
    public void displayOrderList(List<Order> orderList) {
        orderList.stream().
                forEach(o -> {
                    io.print("Order Number: " + o.getOrderNumber()
                            + ", Customer Name: " + o.getCustomerName()
                            + ", State: " + o.getState()
                            + ", Tax Rate: " + o.getTaxRate()
                            + ", Product Type: " + o.getProductType()
                            + ", Area: " + o.getArea()
                            + ", Cost Per Square Foot: " + o.getCostPerSquareFoot()
                            + ", Labor Cost Per Square Foot: " + o.getLaborCostPerSquareFoot()
                            + ", Material Cost: " + o.getMaterialCost()
                            + ", Labor Cost: " + o.getLaborCost()
                            + ", Tax: " + o.getTax()
                            + ", Total: " + o.getTotal() + "\n");
                });
    }
    
    public void displayAddOrderBanner() {
        io.print("*** Add Order ***");
    }
    
    public LocalDate getDateChoice() {
        return io.readDate("Please enter a date (MM/DD/YYYY)");
    }
    
    public Order getNewOrderInfo(int orderNumber) {
        LocalDate date = io.readDate("Please enter Order Date (MM/DD/YYYY)");
        String customerName = io.readString("Please enter Customer Name");
        String state = io.readString("Please enter State");
        String productType = io.readString("Please enter Product Type");
        BigDecimal area = io.readBigDecimal("Please enter Area");
        area = area.setScale(2, RoundingMode.HALF_UP);
        Order currentOrder = new Order(orderNumber);
        currentOrder.setDate(date);
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductType(productType);
        currentOrder.setArea(area);
        return currentOrder;
    }
    
    public void displayOrder(Order order) {
        String date = order.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        io.print("Order:");
        io.print("Date: " + date
                + ", Customer Name: " + order.getCustomerName()
                + ", State: " + order.getState()
                + ", Product Type: " + order.getProductType()
                + ", Area: " + order.getArea());
    }
    
    public boolean getAddConfirmation() {
        String commit;
        
        do {
            commit = io.readString("Would you like to add this order? (Y/N)");
        } while(!commit.equalsIgnoreCase("y") && !commit.equalsIgnoreCase("n"));

        return commit.equalsIgnoreCase("y");
    }
    
    public void displayAddSuccessBanner() {
        io.print("Order successfully added.");
    }
    
    public void displayEditOrderBanner() {
        io.print("*** Edit Order ***");
    }
    
    public LocalDate getDate() {
        return io.readDate("Please enter Order Date (MM/DD/YYYY)");
    }
    
    public int getOrderNumber() {
        return io.readInt("Please enter Order Number");
    }
    
    public Order getEditOrderInfo(Order order) {
        Order editedOrder = new Order(order.getOrderNumber());
        String dateString = order.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate date = io.readDate("Enter date (" + dateString + "):", order.getDate());
        String customerName = io.readString("Enter customer name (" + order.getCustomerName() + "):", order.getCustomerName());
        String state = io.readString("Enter state (" + order.getState() + "):", order.getState());
        String productType = io.readString("Enter product type (" + order.getProductType() + "):", order.getProductType());
        BigDecimal area = io.readBigDecimal("Enter area (" + order.getArea() + "):", order.getArea());
        editedOrder.setDate(date);
        editedOrder.setCustomerName(customerName);
        editedOrder.setState(state);
        editedOrder.setProductType(productType);
        editedOrder.setArea(area);
        
        return editedOrder;
    }
    
    public void displayEditSuccessBanner() {
        io.print("Order successfully edited.");
    }
    
    public void displayRemoveOrderBanner() {
        io.print("*** Remove Order ***");
    }
    
    public boolean getRemoveConfirmation() {
        String commit;
        
        do {
            commit = io.readString("Would you like to remove this order? (Y/N)");
        } while(!commit.equalsIgnoreCase("y") && !commit.equalsIgnoreCase("n"));

        return commit.equalsIgnoreCase("y");
    }
    
    public void displayRemoveSuccessBanner() {
        io.print("Order successfully removed.");
    }
    
    public void displaySaveSuccessBanner() {
        io.print("Work successfully saved.");
    }
    
    public void displayExitBanner() {
        io.print("Goodbye!");
    }
    
    public void displayUnknownCommandBanner() {
       io.print("Unknown Command!"); 
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("*** Error ***");
        io.print(errorMsg);
    }
    
}
