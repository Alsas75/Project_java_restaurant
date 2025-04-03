package client;

import app.controller.DishController;

import java.util.Scanner;

public class Client {

    private static DishController dishController;
//    private static CustomerController customerController;
    private static Scanner scanner;

    public static void main(String[] args) {

        try {
            // Создаём объекты контроллеров для взаимодействия с приложением
            dishController = new DishController();
//            customerController = new CustomerController();
            scanner = new Scanner(System.in);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        while (true) {
            System.out.println("Выберите желаемую операцию:");
            System.out.println("1 - операции с блюдами");
            System.out.println("2 - операции с заказами");
            System.out.println("3 - операции с клиентами");

            System.out.println("0 - выход");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    dishOperations();
                    break;
//                case "2":
//                    orderOperations();
//                    break;
//                case "3":
//                    customerOperations();
//                    break;
                case "0":
                    return;
                default:
                    System.out.println("Некорректный ввод!");
                    break;
            }
        }
    }

    public static void dishOperations() {
        while (true) {
            try {
                System.out.println("Выберите желаемую операцию с блюдами:");
                System.out.println("1 - добавить блюдо");
                System.out.println("2 - получить список блюд");
                System.out.println("3 - получить блюдо по идентификатору");
                System.out.println("4 - изменить блюдо");
                System.out.println("5 - удалить блюдо по идентификатору");
                System.out.println("6 - удалить блюдо по названию");
                System.out.println("7 - восстановить блюдо по идентификатору");
                System.out.println("8 - получить количество блюд");
                System.out.println("9 - получить суммарную стоимость всех блюд");
//                System.out.println("10 - получить среднюю стоимость продукта");
                System.out.println("0 - выход");

                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("Введите название блюда");
                        String title = scanner.nextLine();
                        System.out.println("Введите цену блюда");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.println(dishController.save(title, price));
                        break;
                    case "2":
                        dishController.getAllActiveDishes().forEach(System.out::println);
                        break;
                    case "3":
                        System.out.println("Введите идентификатор блюда");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.println(dishController.getActiveDishById(id));
                        break;
                    case "4":
                        System.out.println("Введите идентификатор блюда");
                        id = Integer.parseInt(scanner.nextLine());
                        System.out.println("Введите новую цену блюда");
                        price = Double.parseDouble(scanner.nextLine());
                        dishController.update(id, price);
                        break;
                    case "5":
                        System.out.println("Введите идентификатор продукта");
                        id = Integer.parseInt(scanner.nextLine());
                        dishController.deleteById(id);
                        break;
                    case "6":
                        System.out.println("Введите название блюда");
                        title = scanner.nextLine();
                        dishController.deleteByTitle(title);
                        break;
                    case "7":
                        System.out.println("Введите идентификатор блюда");
                        id = Integer.parseInt(scanner.nextLine());
                        dishController.restoreById(id);
                        break;
                    case "8":
                        System.out.println("Количество блюд - " + dishController.getActiveDishesCount());
                        break;
                    case "9":
                        System.out.println("Суммарная стоимость блюд - " +
                                dishController.getActiveDishesTotalCost());
                        break;
//                    case "10":
//                        System.out.println("Средняя стоимость продукта - " +
//                                dishController.getActiveProductsAveragePrice());
//                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Некорректный ввод!");
                        break;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

//    public static void customerOperations() {
//        while (true) {
//            try {
//                System.out.println("Выберите желаемую операцию с клиентами:");
//                System.out.println("1 - сохранить клиента");
//                System.out.println("2 - получить всех покупателей");
//                System.out.println("3 - получить покупателя по идентификатору");
//                System.out.println("4 - изменить покупателя");
//                System.out.println("5 - удалить покупателя по идентификатору");
//                System.out.println("6 - удалить покупателя по имени");
//                System.out.println("7 - восстановить покупателя по идентификатору");
//                System.out.println("8 - получить количество покупателей");
//                System.out.println("9 - получить стоимость корзины покупателя");
//                System.out.println("10 - получить среднюю стоимость продукта в корзине покупателя");
//                System.out.println("11 - добавить товар в корзину покупателя");
//                System.out.println("12 - удалить товар из корзины покупателя");
//                System.out.println("13 - очистить корзину покупателя");
//                System.out.println("0 - выход");
//
//                String input = scanner.nextLine();
//
//                switch (input) {
//                    case "1":
//                        System.out.println("Введите имя покупателя");
//                        String name = scanner.nextLine();
//                        System.out.println(customerController.save(name));
//                        break;
//                    case "2":
//                        customerController.getAllActiveCustomers().forEach(System.out::println);
//                        break;
//                    case "3":
//                        System.out.println("Введите идентификатор");
//                        int id = Integer.parseInt(scanner.nextLine());
//                        System.out.println(customerController.getActiveCustomerById(id));
//                        break;
//                    case "4":
//                        System.out.println("Введите идентификатор");
//                        id = Integer.parseInt(scanner.nextLine());
//                        System.out.println("Введите новое имя покупателя");
//                        name = scanner.nextLine();
//                        customerController.update(id, name);
//                        break;
//                    case "5":
//                        System.out.println("Введите идентификатор");
//                        id = Integer.parseInt(scanner.nextLine());
//                        customerController.deleteById(id);
//                        break;
//                    case "6":
//                        System.out.println("Введите имя покупателя");
//                        name = scanner.nextLine();
//                        customerController.deleteByName(name);
//                        break;
//                    case "7":
//                        System.out.println("Введите идентификатор");
//                        id = Integer.parseInt(scanner.nextLine());
//                        customerController.restoreById(id);
//                        break;
//                    case "8":
//                        System.out.println("Количество покупателей - " + customerController.getActiveCustomersNumber());
//                        break;
//                    case "9":
//                        System.out.println("Введите идентификатор");
//                        id = Integer.parseInt(scanner.nextLine());
//                        System.out.println("Стоимость корзины покупателя - " +
//                                customerController.getCustomersCartTotalPrice(id));
//                        break;
//                    case "10":
//                        System.out.println("Введите идентификатор");
//                        id = Integer.parseInt(scanner.nextLine());
//                        System.out.println("Средняя цена продукта в корзине - " +
//                                customerController.getCustomersCartAveragePrice(id));
//                        break;
//                    case "11":
//                        System.out.println("Введите идентификатор покупателя");
//                        int customerId = Integer.parseInt(scanner.nextLine());
//                        System.out.println("Введите идентификатор продукта");
//                        int productId = Integer.parseInt(scanner.nextLine());
//                        customerController.addProductToCustomersCart(customerId, productId);
//                        break;
//                    case "12":
//                        System.out.println("Введите идентификатор покупателя");
//                        customerId = Integer.parseInt(scanner.nextLine());
//                        System.out.println("Введите идентификатор продукта");
//                        productId = Integer.parseInt(scanner.nextLine());
//                        customerController.removeProductFromCustomersCart(customerId, productId);
//                        break;
//                    case "13":
//                        System.out.println("Введите идентификатор покупателя");
//                        id = Integer.parseInt(scanner.nextLine());
//                        customerController.clearCustomersCart(id);
//                        break;
//                    case "0":
//                        return;
//                    default:
//                        System.out.println("Некорректный ввод!");
//                        break;
//                }
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//            }
//        }
//    }
}
