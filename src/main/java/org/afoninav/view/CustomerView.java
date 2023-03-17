package org.afoninav.view;

import jdk.swing.interop.SwingInterOpUtils;
import org.afoninav.controller.CustomerController;
import org.afoninav.model.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerView implements GenericView {

    private Scanner scanner;
    private CustomerController customerController;
    private MainView mainView;

    public CustomerView(MainView mainView) {
        customerController = new CustomerController();
        scanner = new Scanner(System.in);
        this.mainView = mainView;
    }

    @Override
    public void actions() {
        showMenu();
        translateUserInput();
    }

    @Override
    public void showMenu() {
        String menu = "Enter menu number:\n" +
                "1. Create customer's profile;\n" +
                "2. Find customer's profile by id;\n" +
                "3. Show all customers profiles;\n" +
                "4. Update customer's profile;\n" +
                "5. Delete customer's profile;\n" +
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
        System.out.println("Enter new name:");
        String name = scanner.nextLine();
        System.out.println("Enter new phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter new email:");
        String email = scanner.nextLine();
        System.out.println("Enter new address:");
        String address = scanner.nextLine();
        boolean result = customerController.create(new Customer(name,phone,email,address));
        System.out.println(result ? "CREATED 1\n" : "CREATED 0\n");
    }

    void readById() {
        System.out.println("Enter customer id for read:");
        Long id = Long.parseLong(scanner.nextLine());
        Customer customer = customerController.readByID(id);
        System.out.println(customer + "\n");
    }

    void update() {
        System.out.println("Enter customer id for update:");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println("Enter new name:");
        String name = scanner.nextLine();
        System.out.println("Enter new phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter new email:");
        String email = scanner.nextLine();
        System.out.println("Enter new address:");
        String address = scanner.nextLine();
        boolean result = customerController.update(new Customer(id,name,phone,email,address));
        System.out.println(result ? "UPDATED 1\n" : "UPDATED 0\n");
    }

    void deleteById() {
        System.out.println("Enter customer id for delete:");
        Long id = Long.parseLong(scanner.nextLine());
        boolean result = customerController.deleteById(id);
        System.out.println(result ? "DELETED 1\n" : "DELETED 0\n");
    }

    void readAll() {
        List<Customer> list = customerController.readAll();
        for (Customer c : list) {
            System.out.println(c);
        }
    }
}
