package org.afoninav.view;

import org.afoninav.controller.OrderController;
import org.afoninav.model.Customer;
import org.afoninav.model.Order;
import org.afoninav.model.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderView implements GenericView {

    private final OrderController orderController;
    private final MainView mainView;
    private final Scanner scanner;

    public OrderView(MainView mainView) {
        orderController = new OrderController();
        this.mainView = mainView;
        scanner = new Scanner(System.in);
    }

    @Override
    public void actions() {
        showMenu();
        translateUserInput();
    }

    @Override
    public void showMenu() {
        String menu = "Enter menu number:\n" +
                "1. Create Order's record;\n" +
                "2. Find Order by id;\n" +
                "3. Show all Order records;\n" +
                "4. Update Order's record;\n" +
                "5. Delete Order's record;\n" +
                "0. Exit from program\n";
        System.out.println(menu);;
    }

    @Override
    public void translateUserInput() {
        int userPeek = Integer.parseInt(scanner.nextLine());
        switch (userPeek) {
            case 1:
                create();
                break;
            case 2:
                readById();
                break;
            case 3:
                readAll();
                break;
            case 4:
                update();
                break;
            case 5:
                deleteById();
                break;
            case 0:
                System.out.println("Bye-bye.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid menu's number. Try again.");
        }
    }

    void create() {
        Order order = new Order();
        System.out.println("Enter comment for order:");
        order.setComment(scanner.nextLine());
        System.out.println("Enter customer id for create order:");
        order.setCustomer(new Customer(Long.parseLong(scanner.nextLine())));
        System.out.println("Enter products id to add in order: for example 2,3,4,5,1");
        String[] lines = scanner.nextLine().split(",");
        List<Product> products = Arrays.stream(lines)
                .map(n -> {
                    Long id = Long.parseLong(n);
                    return new Product(id);
                    })
                .collect(Collectors.toList());
        order.setProduct(products);
        boolean result = orderController.create(order);
        System.out.println(result ? "CREATED 1\n" : "CREATED 0\n");
    }

    void readById() {
        System.out.println("Enter order id for read:");
        Long id = Long.parseLong(scanner.nextLine());
        Order order = orderController.readByID(id);
        System.out.println(order);
    }

    void update() {
        Order order = new Order();
        System.out.println("Enter order id for update:");
        order.setId(Long.parseLong(scanner.nextLine()));
        System.out.println("Enter new comment:");
        order.setComment(scanner.nextLine());
        System.out.println("Enter new products: for example 2,3,4,1,5");
        String[] lines = scanner.nextLine().split(",");
        List<Product> products = Arrays.stream(lines)
                .map(n -> {
                    Long id = Long.parseLong(n);
                    return new Product(id);
                })
                .collect(Collectors.toList());
        order.setProduct(products);
        boolean result = orderController.update(order);
        System.out.println(result ? "UPDATED 1\n" : "UPDATED 0\n");
    }

    void deleteById() {
        System.out.println("Enter order id for delete:");
        Long id = Long.parseLong(scanner.nextLine());
        boolean result = orderController.deleteById(id);
        System.out.println(result ? "DELETED 1\n" : "DELETED 0\n");
    }

    void readAll() {
        List<Order> list = orderController.readAll();
        for (Order o : list) {
            System.out.println(o);
        }
    }
}
