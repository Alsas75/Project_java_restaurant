package app.exceptions;

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(int id) {
        super(String.format("Заказ с идентификатором %d не найден", id));
    }
}
