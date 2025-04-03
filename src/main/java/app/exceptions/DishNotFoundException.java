package app.exceptions;

public class DishNotFoundException extends Exception {

    public DishNotFoundException(int id) {
        super(String.format("Блюдо с идентификатором %d не найден", id));
    }

    public DishNotFoundException(String title) {
        super(String.format("Блюдо с названием %s не найден", title));
    }

}


