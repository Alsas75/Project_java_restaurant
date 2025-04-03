package app.repository;

import app.domain.Dish;
import app.domain.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRepository {

    private final File database;
    private final ObjectMapper mapper;
    private int maxId;


    public OrderRepository() throws IOException {
        database = new File("database/order.txt");
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<Order> orders = findAll();
        if (!orders.isEmpty()) {
            Order lastOrder = orders.get(orders.size() - 1);
            maxId = lastOrder.getId();
        }
    }

    public Order save(Order order) throws IOException {
        order.setId(++maxId);
        List<Order> orders = findAll();
        orders.add(order);
        mapper.writeValue(database, orders);
        return order;
    }

    public List<Order> findAll() throws IOException {
        try {
            Order[] orders = mapper.readValue(database, Order[].class);
            return new ArrayList<>(Arrays.asList(orders));

        } catch (MismatchedInputException e) {
            return new ArrayList<>();
        }
    }

    public Order findById(int id) throws IOException {
        return findAll()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void update(Order order) throws IOException {
        int id = order.getId();
        List<Dish> dishes = order.getDishes();

        List<Order> orders = findAll();
        orders
                .stream()
                .filter(x -> x.getId() == id)
                .forEach(x -> x.setDishes(dishes));

        mapper.writeValue(database, orders);
    }

    public void deleteById(int id) throws IOException {
        List<Order> orders = findAll();
        orders.removeIf(x -> x.getId() == id);
        mapper.writeValue(database, orders);
    }
}
