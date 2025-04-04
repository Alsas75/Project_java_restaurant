package app.service;

import app.domain.Dish;
import app.domain.Order;
import app.exceptions.DishNotFoundException;
import app.exceptions.OrderNotFoundException;
import app.exceptions.OrderSaveException;
import app.exceptions.OrderUpdateException;
import app.repository.OrderRepository;

import java.io.IOException;
import java.util.List;

public class OrderService {


    /*
    Это поле с объектом репозитория нужно для того, чтобы код сервиса
    мог обращаться к объекту репозитория и вызывать у него методы
    для взаимодействия с базой данных.
     */
    private final OrderRepository repository;
    private final DishService dishService;

    public OrderService() throws IOException {
        repository = new OrderRepository();
        dishService = new DishService();
    }


    //сохранение заказа
    public Order save(Order order) throws IOException, OrderSaveException {
        if (order == null) {
            throw new OrderSaveException("Заказ не может быть null");
        }

        List<Dish> dishes = order.getDishes();
        if (dishes == null || dishes.isEmpty()) {
            throw new OrderSaveException("Список блюд не может быть null");
        }

        order.setActive(true);
        return repository.save(order);
    }

    //    Вернуть все заказы из базы данных (активные).
    public List<Order> getAllActiveOrders() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Order::isActive)
//                .filter(x -> x.isActive())
                .toList();
    }

    //    Вернуть один заказ из базы данных по его идентификатору (если активен).
    public Order getActiveOrderById(int id) throws IOException, OrderNotFoundException {
        Order order = repository.findById(id);

        if (order == null || !order.isActive()) {
            throw new OrderNotFoundException(id);
        }

        return order;
    }

    //    Изменить один заказ в базе данных по его идентификатору.
    public void update(Order order) throws IOException, OrderUpdateException {
        if (order == null) {
            throw new OrderUpdateException("Заказ не может быть null");
        }

        if (order.getDishes().isEmpty()) {
            throw new OrderUpdateException("Список блюд не может быть пустым");
        }

        order.setActive(true);
        repository.update(order);
    }

    //    Добавить блюдо в заказ по его идентификатору.
    public void addDishInOrderDyId(int orderId, int dishId) throws IOException, OrderUpdateException, OrderNotFoundException, DishNotFoundException {
        Order order = getActiveOrderById(orderId);
        Dish dish = dishService.getActiveDishById(dishId);
        order.getDishes().add(dish);
        repository.update(order);
    }

    //    Удалить заказ по его идентификатору.
    public void deleteById(int id) throws IOException, OrderNotFoundException {
        Order order = getActiveOrderById(id);
        order.setActive(false);
        repository.update(order);
    }

    //    Удалить блюдо из заказа по его идентификатору.
//    public void deleteDishFromOrderDyId(int orderId, int dishId) throws IOException, OrderUpdateException, OrderNotFoundException {
//        Order order = getActiveOrderById(orderId);
//        boolean isDishRemove = order.getDishes().removeIf(x -> x.getId() == dishId);
//
//        if (!isDishRemove) {
//            throw new OrderUpdateException("Удаляемое блюдо в заказе не найдено");
//        } else {
//            repository.update(order);
//        }
//    }

    //    Восстановить заказ в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, OrderNotFoundException {
        Order order = repository.findById(id);
        if (order != null) {
            order.setActive(true);
            repository.update(order);
        } else {
            throw new OrderNotFoundException(id);
        }
    }

    //    Вернуть общее количество заказов в базе данных (активных).
    public int getActiveOrdersCount() throws IOException {
        return getAllActiveOrders().size();
    }

    //    Вернуть стоимость заказа по id (активного).
    public double getActiveOrderCostById(int id) throws IOException, OrderNotFoundException {
        return getActiveOrderById(id).getDishes()
                .stream()
                .mapToDouble(Dish::getPrice)
                .sum();
    }

    //    Вернуть суммарную стоимость всех заказов в базе данных (активных).
    public double getOrdersTotalCost() throws IOException {
        return repository.findAll()
                .stream()
                .flatMap(x -> x.getDishes()
                        .stream())
                .mapToDouble(Dish::getPrice)
                .sum();
    }
}
