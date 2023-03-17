package org.afoninav.view;

import org.afoninav.controller.ProductController;
import org.afoninav.model.Product;

import java.util.List;
import java.util.Scanner;

public class ProductView implements GenericView {

    private final ProductController productController;
    private final MainView mainView;
    private final Scanner scanner;

    public ProductView(MainView mainView) {
        productController = new ProductController();
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
                "1. Create Product's record;\n" +
                "2. Find Product by id;\n" +
                "3. Show all Products;\n" +
                "4. Update Product's record;\n" +
                "5. Delete Product's record;\n" +
                "0. Exit from program\n";
        System.out.println(menu);;
    }

    @Override
    public void translateUserInput() {
        int userPeek = scanner.nextInt();
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
        Product product = new Product();
        System.out.println("Enter product's name:");
        scanner.nextLine();
        product.setName(scanner.nextLine());
        System.out.println("Enter product's price:");
        product.setPrice(Double.parseDouble(scanner.nextLine()));
        boolean result = productController.create(product);
        System.out.println(result ? "CREATED 1\n" : "CREATED 0\n");
    }

    void readById() {
        System.out.println("Enter product's id:");
        Long id = scanner.nextLong();
        Product product = productController.readByID(id);
        System.out.println(product);
    }

    void update() {
        Product product = new Product();
        System.out.println("Enter product's id for update:");
        product.setId(Long.parseLong(scanner.nextLine()));
        System.out.println("Enter product's name for update:");
        product.setName(scanner.nextLine());
        System.out.println("Enter product's price for update:");
        product.setPrice(Double.parseDouble(scanner.nextLine()));
        boolean result = productController.update(product);
        System.out.println(result ? "UPDATED 1\n" : "UPDATED 0\n");
    }

    void deleteById() {
        Long id = Long.parseLong(scanner.nextLine());
        boolean result = productController.deleteById(id);
        System.out.println(result ? "DELETED 1\n" : "DELETED 0\n");
    }

    void readAll() {
        List<Product> products = productController.readAll();
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
