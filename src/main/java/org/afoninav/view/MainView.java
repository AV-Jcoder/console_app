package org.afoninav.view;

import org.afoninav.model.Product;

import java.util.Scanner;

public class MainView implements GenericView {

    private final Scanner scanner;
    private final CustomerView customerView;
    private final OrderView orderView;
    private final ProductView productView;

    public MainView() {
        scanner = new Scanner(System.in);
        customerView = new CustomerView(this);
        orderView = new OrderView(this);
        productView = new ProductView(this);
    }

    @Override
    public void actions() {
        while(true) {
            showMenu();
            translateUserInput();
        }
    }

    @Override
    public void showMenu() {
        String menu = "Enter menu number:\n" +
                "1. Customer menu.\n" +
                "2. Order menu.\n" +
                "3. Product menu.\n" +
                "0. Exit program.\n";
        System.out.println(menu);
    }

    @Override
    public void translateUserInput() {
        int userPeek = Integer.parseInt(scanner.nextLine());
        switch (userPeek) {
            case 1:
                customerView.actions();
                break;
            case 2:
                orderView.actions();
                break;
            case 3:
                productView.actions();
                break;
            case 0:
                System.out.println("Bye-bye.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid menu's number. Try again.");
        }
    }
}
