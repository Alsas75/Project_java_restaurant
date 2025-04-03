package app.controller;

import app.domain.Dish;
import app.exceptions.DishNotFoundException;
import app.exceptions.DishSaveException;
import app.exceptions.DishUpdateException;
import app.service.DishService;

import java.io.IOException;
import java.util.List;

public class DishController {

    private final DishService service;

    public DishController() throws IOException {
        service = new DishService();
    }

    public Dish save(String title, double price) throws IOException, DishSaveException {
        Dish dish = new Dish(title, price);
        return service.save(dish);
    }

    public List<Dish> getAllActiveDishes() throws IOException {
        return service.getAllActiveDishes();
    }

    public Dish getActiveDishById(int id) throws IOException, DishNotFoundException {
        return service.getActiveDishById(id);
    }

    public void update(int id, double price) throws IOException, DishUpdateException {
        Dish dish = new Dish(id, price);
        service.update(dish);
    }

    public void deleteById(int id) throws IOException, DishNotFoundException {
        service.deleteById(id);
    }

    public void deleteByTitle(String title) throws IOException, DishNotFoundException {
        service.deleteByTitle(title);
    }

    public void restoreById(int id) throws IOException, DishNotFoundException {
        service.restoreById(id);
    }

    public int getActiveDishesCount() throws IOException {
        return service.getActiveDishesCount();
    }

    public double getActiveDishesTotalCost() throws IOException {
        return service.getActiveDishesTotalCost();
    }

//    public double getActiveProductsAveragePrice() throws IOException {
//        return service.getActiveProductsAveragePrice();
//    }

}
