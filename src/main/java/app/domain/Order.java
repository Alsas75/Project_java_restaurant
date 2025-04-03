package app.domain;

import java.util.List;
import java.util.Objects;

public class Order {

    private int id;
    private List<Dish> dishes;
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(dishes, order.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dishes);
    }

    @Override
    public String toString() {
        return String.format("Заказ: ид - %d, список блюд - %s, активен - %b.",
                id, dishes, active);
    }
}
