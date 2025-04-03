package app.service;

import app.domain.Customer;
import app.domain.Dish;
import app.domain.Order;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.CustomerSaveException;
import app.exceptions.CustomerUpdateException;
import app.repository.CustomerRepository;

import java.io.IOException;
import java.util.List;

public class CustomerService {

    private final CustomerRepository repository;
//    private final ProductService productService;

    public CustomerService() throws IOException {
        repository = new CustomerRepository();
//        productService = new ProductService();
    }

    //    Сохранить клиента в базе данных (при сохранении клиент автоматически считается активным).
    public Customer save(Customer customer) throws CustomerSaveException, IOException {
        if (customer == null) {
            throw new CustomerSaveException("Клиент не может быть null");
        }

        String name = customer.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new CustomerSaveException("Имя клиента не может быть пустым");
        }

        customer.setActive(true);
        return repository.save(customer);
    }

    //    Вернуть всех клиентов из базы данных (активных).
    public List<Customer> getAllActiveCustomers() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .toList();
    }

    //    Вернуть одного клиента из базы данных по его идентификатору (если он активен).
    public Customer getActiveCustomerById(int id) throws IOException, CustomerNotFoundException {
        Customer customer = repository.findById(id);
        if (customer == null || !customer.isActive()) {
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }

    //    Изменить одного клиента в базе данных по его идентификатору.
    public void update(Customer customer) throws CustomerUpdateException, IOException {
        if (customer == null) {
            throw new CustomerUpdateException("Клиент не может быть null");
        }

        String name = customer.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new CustomerUpdateException("Имя клиента не может быть пустым");
        }
        customer.setActive(true);
        repository.update(customer);
    }

    //    Удалить клиента из базы данных по его идентификатору.
    public void deleteById(int id) throws IOException, CustomerNotFoundException {
        Customer customer = getActiveCustomerById(id);
        customer.setActive(false);
        repository.update(customer);
    }

    //    Восстановить удалённого клиента в базе данных по его идентификатору.
    public void restoreById(int id) throws IOException, CustomerNotFoundException {
        Customer customer = repository.findById(id);

        if (customer != null) {
            customer.setActive(true);
            repository.update(customer);
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    //    Вернуть общее количество клиентов в базе данных (активных).
    public int getActiveCustomersNumber() throws IOException {
        return getAllActiveCustomers().size();
    }

    //    Вернуть общую стоимость одного заказа клиента по его идентификатору и номеру заказа(если он активен).
    public double getCustomerOrderTotalPrice(int customerId, int orderId) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(customerId)
                .getOrders()
                .stream()
                .filter(o -> o.isActive() && o.getId() == orderId)
                .flatMapToDouble(x -> x.getDishes()
                        .stream()
                        .mapToDouble(Dish::getPrice))
                .sum();
    }

    //    Вернуть общую стоимость заказов клиента по его идентификатору (если он активен).
    public double getCustomerOrdersTotalPrice(int id) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(id)
                .getOrders()
                .stream()
                .filter(Order::isActive)
                .flatMapToDouble(x -> x.getDishes()
                        .stream()
                        .mapToDouble(Dish::getPrice))
                .sum();
    }

    //    Вернуть среднюю стоимость заказов в корзине покупателя по его идентификатору (если он активен)
    public double getCustomersOrdersAveragePrice(int id) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(id)
                .getOrders()
                .stream()
                .filter(Order::isActive)
                .flatMapToDouble(x -> x.getDishes()
                        .stream()
                        .mapToDouble(Dish::getPrice))
                .average()
                .orElse(0.0);
    }

//    //    Добавить блюдо в корзину клиента по его идентификатору (если оба активны)
//    public void addDishToCustomersOrder(int customerId, int discId) throws IOException, CustomerNotFoundException, ProductNotFoundException {
//        Customer customer = getActiveCustomerById(customerId);
//        Product product = productService.getActiveProductById(productId);
//        customer.getProducts().add(product);
//        repository.update(customer);
//    }
//
//    //    Удалить товар из корзины покупателя по их идентификаторам
//    public void removeProductFromCustomersCart(int customerId, int productId) throws IOException, CustomerNotFoundException, ProductNotFoundException {
//        Customer customer = getActiveCustomerById(customerId);
//        Product product = productService.getActiveProductById(productId);
//        customer.getProducts().remove(product);
//        repository.update(customer);
//    }
//
//    //    Полностью очистить корзину покупателя по его идентификатору (если он активен)
//    public void clearCustomersCart(int id) throws IOException, CustomerNotFoundException {
//        Customer customer = getActiveCustomerById(id);
//        customer.getProducts().clear();
//        repository.update(customer);
//    }
}
