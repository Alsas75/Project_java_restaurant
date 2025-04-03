package app.repository;

import app.domain.Dish;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DishRepository {

    // Файл, который является базой данных
    private final File database;

    // Маппер для чтения и записи в файл объектов
    private final ObjectMapper mapper;

    // Поле, которое хранит максимальный идентификатор, сохранённый в базе данных
    private int maxId;

    public DishRepository() throws IOException {
        database = new File("database/dish.txt");
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Выясняем, какой идентификатор в БД на данный момент максимальный
        List<Dish> dishes = findAll();

        if (!dishes.isEmpty()) {
            Dish lastDish = dishes.get(dishes.size() - 1);
            maxId = lastDish.getId();
        }
    }

    // Сохраняем новый продукт в БД
    public Dish save(Dish dish) throws IOException {
        dish.setId(++maxId);
        List<Dish> dishes = findAll();
        dishes.add(dish);
        mapper.writeValue(database, dishes);
        return dish;
    }

    // Читаем из БД все продукты
    public List<Dish> findAll() throws IOException {
        try {
            Dish[] dishes = mapper.readValue(database, Dish[].class);
            return new ArrayList<>(Arrays.asList(dishes));
        } catch (MismatchedInputException e) {
            // Если произошла ошибка MismatchedInputException, это значит,
            // что Джексону не удалось прочитать информацию из файла, потому что он пустой.
            // Раз файл пустой - значит продуктов нет, а значит возвращаем пустой лист.
            return new ArrayList<>();
        }
    }

    // Читаем из БД один продукт по идентификатору
    public Dish findById(int id) throws IOException {
        return findAll()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Обновление существующего продукта в БД
    // Этот метод будет менять только цену
    public void update(Dish dish) throws IOException {
        int id = dish.getId();
        double newPrice = dish.getPrice();
        boolean active = dish.isActive();

        List<Dish> dishes = findAll();
        dishes
                .stream()
                .filter(x -> x.getId() == id)
                .forEach(x -> {
                    x.setPrice(newPrice);
                    x.setActive(active);
                });
        mapper.writeValue(database, dishes);
    }

    // Удаляем продукт из БД
    public void deleteById(int id) throws IOException {
        List<Dish> dishes = findAll();
        dishes.removeIf(x -> x.getId() == id);
        mapper.writeValue(database, dishes);
    }
}

