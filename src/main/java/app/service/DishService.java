package app.service;

import app.domain.Dish;
import app.exceptions.DishNotFoundException;
import app.exceptions.DishSaveException;
import app.exceptions.DishUpdateException;
import app.repository.DishRepository;

import java.io.IOException;
import java.util.List;

public class DishService {

    /*
    Это поле с объектом репозитория нужно для того, чтобы код сервиса
    мог обращаться к объекту репозитория и вызывать у него методы
    для взаимодействия с базой данных.
     */
    private final DishRepository repository;

    public DishService() throws IOException {
        repository = new DishRepository();
    }


    public Dish save(Dish dish) throws DishSaveException, IOException {
        if (dish == null) {
            throw new DishSaveException("Блюдо не может быть null");
        }

        String title = dish.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new DishSaveException("Наименование блюда не может быть пустым");
        }

        if (dish.getPrice() <= 0) {
            throw new DishSaveException("Цена блюда должна быть положительной");
        }

        dish.setActive(true);
        return repository.save(dish);
    }

    //    Вернуть все блюда из базы данных (активные).
    public List<Dish> getAllActiveDishes() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Dish::isActive)
//                .filter(x -> x.isActive())
                .toList();
    }

    //    Вернуть одно блюдо из базы данных по его идентификатору (если оно активно).
    public Dish getActiveDishById(int id) throws IOException, DishNotFoundException {
        Dish dish = repository.findById(id);

        if (dish == null || !dish.isActive()) {
            throw new DishNotFoundException(id);
        }

        return dish;
    }

    //    Изменить одно блюдо в базе данных по его идентификатору.
    public void update(Dish dish) throws IOException, DishUpdateException {
        if (dish == null) {
            throw new DishUpdateException("Блюдо не может быть null");
        }

        if (dish.getPrice() <= 0) {
            throw new DishUpdateException("Цена блюда должна быть положительной");
        }

        dish.setActive(true);
        repository.update(dish);
    }

    //    Удалить блюдо из базы данных по его идентификатору.
    public void deleteById(int id) throws IOException, DishNotFoundException {
        Dish dish = getActiveDishById(id);
        dish.setActive(false);
        repository.update(dish);
    }

    //    Удалить блюдо из базы данных по его наименованию.
    public void deleteByTitle(String title) throws IOException, DishNotFoundException {
        Dish dish = getAllActiveDishes()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .peek(x -> x.setActive(false))
                .findFirst()
                .orElseThrow(
                        () -> new DishNotFoundException(title)
                );
        repository.update(dish);
    }

    //    Восстановить удалённое блюдо в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, DishNotFoundException {
        Dish dish = repository.findById(id);
        if (dish != null) {
            dish.setActive(true);
            repository.update(dish);
        } else {
            throw new DishNotFoundException(id);
        }
    }

    //    Вернуть общее количество блюд в базе данных (активных).
    public int getActiveDishesCount() throws IOException {
        return getAllActiveDishes().size();
    }

    //    Вернуть суммарную стоимость всех блюд в базе данных (активных).
    public double getActiveDishesTotalCost() throws IOException {
        return getAllActiveDishes()
                .stream()
                .mapToDouble(Dish::getPrice)
                .sum();
    }

//    //    Вернуть среднюю стоимость блюда в базе данных (из активных).
//    public double getActiveDishesAveragePrice() throws IOException {
//        // 1 способ
////        return getAllActiveProducts()
////                .stream()
////                .mapToDouble(Product::getPrice)
////                .average()
////                .orElse(0.0);
//
//        // 2 способ
//        int productCount = getActiveProductsCount();
//
//        if (productCount == 0) {
//            return 0.0;
//        }
//
//        return getActiveProductsTotalCost() / productCount;
//    }

}
