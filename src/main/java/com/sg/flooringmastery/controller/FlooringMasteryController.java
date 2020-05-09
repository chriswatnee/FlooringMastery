/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.FlooringMasteryDataValidationException;
import com.sg.flooringmastery.service.FlooringMasteryNoOrderException;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public class FlooringMasteryController {
    
    private FlooringMasteryServiceLayer service;
    private FlooringMasteryView view;
    
    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }
    
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        
        try {
            loadConfig();
            setMode();
            loadData();
            while(keepGoing) {
                menuSelection = getMenuSelection();

                switch(menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        saveWork();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
            saveWorkNoMessage();
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    private void loadConfig() throws FlooringMasteryPersistenceException {
        service.loadConfig();
    }
    
    private void setMode() {
        try {
            service.setMode();
        } catch (FlooringMasteryDataValidationException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    private void loadData() throws FlooringMasteryPersistenceException {
        service.loadData();
    }
    
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void displayOrders() {
        view.displayDisplayOrdersBanner();
        LocalDate date = view.getDateChoice();
        try {
            List<Order> orderList = service.getOrders(date);
            view.displayOrderList(orderList);
            view.displayReturnToMainMenu();
        } catch (FlooringMasteryNoOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    private void addOrder() {
        Order currentOrder;
        view.displayAddOrderBanner();
        boolean hasErrors;
        // Get new order information until it is valid
        do {
            currentOrder = view.getNewOrderInfo(service.getNewOrderNumber());
            try {
                // Validate the state and product type fields on the given Order object.
                // This method will throw an exception if any of the validation rules are violated.
                service.validateOrderData(currentOrder);
                hasErrors = false;
            } catch (FlooringMasteryDataValidationException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);
        // Display order information and get confirmation
        view.displayOrder(currentOrder);
        boolean confirmation = view.getAddConfirmation();
        // If confirmed then add order
        if (confirmation) {
            service.addOrder(currentOrder);
            view.displayAddSuccessBanner();
            view.displayReturnToMainMenu();
        }
    }
    
    private void editOrder() {
        Order originalOrder;
        Order editedOrder;
        view.displayEditOrderBanner();
        LocalDate date = view.getDateChoice();
        int orderNumber = view.getOrderNumber();
        boolean hasErrors;
        
        try {
            // Get the order with the given date and order number.
            // This method will throw an exception if there is none.
            originalOrder = service.getOrder(date, orderNumber);
        } catch (FlooringMasteryNoOrderException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }
        
        // Get edited order information until it is valid
        do {
            editedOrder = view.getEditOrderInfo(originalOrder);
            try {
                // Validate the state and product type fields on the given Order object.
                // This method will throw an exception if any of the validation rules are violated.
                service.validateOrderData(editedOrder);
                hasErrors = false;
            } catch (FlooringMasteryDataValidationException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);

        // Edit order
        service.editOrder(editedOrder);
        view.displayEditSuccessBanner();
        view.displayReturnToMainMenu();
    }
    
    private void removeOrder() {
        Order order;
        view.displayRemoveOrderBanner();
        LocalDate date = view.getDateChoice();
        int orderNumber = view.getOrderNumber();
        
        try {
            // Get the order with the given date and order number.
            // This method will throw an exception if there is none.
            order = service.getOrder(date, orderNumber);
            // Display order information and get confirmation
            view.displayOrder(order);
            boolean confirmation = view.getRemoveConfirmation();
            // If confirmed then remove order
            if (confirmation) {
                service.removeOrder(orderNumber);
                view.displayRemoveSuccessBanner();
                view.displayReturnToMainMenu();
            }
        } catch (FlooringMasteryNoOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    private void saveWork() throws FlooringMasteryPersistenceException {
        service.writeOrders();
        view.displaySaveSuccessBanner();
        view.displayReturnToMainMenu();
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    private void saveWorkNoMessage() throws FlooringMasteryPersistenceException {
        service.writeOrders();
    }
    
}
