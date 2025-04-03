package app.domain;

import java.util.List;
import java.util.Objects;

public class Customer {
    private int id;
    private String name;
    private boolean active;
    private List<Order> orders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Order> getOrders() { return orders; }

    public void setOrders(List<Order> orders) { this.orders = orders; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && active == customer.active && Objects.equals(name, customer.name) && Objects.equals(orders, customer.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active, orders);
    }

    @Override
    public String toString() {
        return String.format("Покупатель: ид - %d, имя - %s, активен - %b, список заказов - %s.",
                id, name, active, orders);
    }
}
