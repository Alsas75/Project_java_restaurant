package app.controller;

import app.domain.Order;
import app.exceptions.OrderNotFoundException;
import app.exceptions.OrderSaveException;
import app.service.OrderService;

import java.io.IOException;
import java.util.List;

public class OrderController {

    private final OrderService service;

    public OrderController() throws IOException {
        service = new OrderService();
    }

    public Order save() throws IOException, OrderSaveException {
        Order order = new Order();
        return service.save(order);
    }

    public List<Order> getAllActiveOrders() throws IOException {
        return service.getAllActiveOrders();
    }

    public Order getActiveOrderById(int id) throws IOException, OrderNotFoundException {
        return service.getActiveOrderById(id);
    }

//    public void update(int id, double price) throws IOException, DishUpdateException {
//        Dish dish = new Dish(id, price);
//        service.update(dish);
//    }

    public void deleteById(int id) throws IOException, OrderNotFoundException {
        service.deleteById(id);
    }

    public void restoreById(int id) throws IOException, OrderNotFoundException {
        service.restoreById(id);
    }

    public int getActiveOrderCount() throws IOException {
        return service.getActiveOrdersCount();
    }

    public double getActiveOrderCostById(int id) throws IOException, OrderNotFoundException {
        return service.getActiveOrderCostById(id);
    }

    public double getOrdersTotalCost() throws IOException, OrderNotFoundException {
        return service.getOrdersTotalCost();
    }

//    public double getActiveProductsAveragePrice() throws IOException {
//        return service.getActiveProductsAveragePrice();
//    }
}
