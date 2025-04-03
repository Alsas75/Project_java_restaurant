package app.exceptions;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException(String message) {
        super(message);
    }
    public CustomerNotFoundException(int id) {
        super(String.format("Клиент с идентификатором %d не найден", id));
    }
}
