package controller;

import model.Account;
import service.impl.*;

import java.util.Scanner;

public class MainRun {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ManageRole manageRole = new ManageRole(scanner);
        ManageAccount manageAccount = new ManageAccount(scanner, manageRole);
        ManageColor manageColor = new ManageColor(scanner);
        ManageProduct manageProduct = new ManageProduct(scanner, manageColor);
        ManageCart manageCart = new ManageCart(manageProduct);
        loginAccount(scanner, manageAccount, manageProduct, manageColor, manageCart);
    }

    public static Account accountPresent;

    public static void login(Scanner scanner, ManageAccount manageAccount, ManageProduct manageProduct, ManageColor manageColor, ManageCart manageCart) {
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        accountPresent = manageAccount.login(new Account(username, password));

        if (accountPresent != null) {
            System.out.println("Hello " + accountPresent.getFullName());
            if (accountPresent.getRole().getName().equals("Admin")) {
                menuAdmin(scanner, manageAccount, manageProduct, manageColor, manageCart);
            } else {
                menuUser(scanner, manageAccount, manageProduct, manageCart);
            }
        } else {
            System.err.println("Account does not exist");
        }
    }

    private static void loginAccount(Scanner scanner, ManageAccount manageAccount, ManageProduct manageProduct, ManageColor manageColor, ManageCart manageCart) {
        int choice = -1;
        do {
            System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _");
            System.out.println("|_ _ _ _ _ Menu_ _ _ _ _ _|");
            System.out.println("|       1. Login          |");
            System.out.println("|       2. Register       |");
            System.out.println("|       0. Exit!          |");
            System.out.println("|_ _ _ _ _ _(1)_ _ _ _ _ _|");
            System.out.println("-->Enter your choice here!<--");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Please re-enter the number!");
                loginAccount(scanner, manageAccount, manageProduct, manageColor, manageCart);
            }

            switch (choice) {
                case 1:
                    login(scanner, manageAccount, manageProduct, manageColor, manageCart);
                    break;
                case 2:
                    manageAccount.create();
                    break;
                case 0:
                    System.exit(0);
            }
        } while (true);
    }

    private static void menuAdmin(Scanner scanner, ManageAccount manageAccount, ManageProduct manageProduct, ManageColor manageColor, ManageCart manageCart) {
        int choice = -1;
        do {
            System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _");
            System.out.println("|_ _ _ _ _ Menu_ _ _ _ _|");
            System.out.println("| 1. Account Management |");
            System.out.println("| 2. Product Management |");
            System.out.println("| 0. Log Out!           |");
            System.out.println("|_ _ _ _ _ (2) _ _ _ _ _|");

            System.out.println("-->Enter your choice here!<--");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Please re-enter the number!");
            }

            switch (choice) {
                case 1:
                    menuAdminAboutAcc(scanner, manageAccount);
                    break;
                case 2:
                    menuAdminAboutProduct(scanner, manageProduct, manageColor, manageCart);
                    break;
            }
        } while (choice != 0);
    }

    private static void menuAdminAboutAcc(Scanner scanner, ManageAccount manageAccount) {
        int choice = -1;
        do {
            System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
            System.out.println("| _ _ _ _ Menu Account Management _ _ _ _ _ |");
            System.out.println("|         1. Display all ACC                |");
            System.out.println("|         2. Create account                 |");
            System.out.println("|         3. Update account                 |");
            System.out.println("|         4. Delete account                 |");
            System.out.println("|         5. Find account by username       |");
            System.out.println("|         6. Find account by id             |");
            System.out.println("|         7. Sort accounts by username      |");
            System.out.println("|         8. Sort accounts by id            |");
            System.out.println("|         0. Exit!                          |");
            System.out.println("| _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |");
            System.out.println("         -->Enter your choice here!<--     ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please re-enter the number!");
            }

            switch (choice) {
                case 1:
                    manageAccount.displayAll();
                    break;
                case 2:
                    manageAccount.create();
                    break;
                case 3:
                    manageAccount.update();
                    break;
                case 4:
                    manageAccount.delete();
                    break;
                case 5:
                    manageAccount.searchByName();
                    break;
                case 6:
                    manageAccount.searchById();
                    break;
                case 7:
                    manageAccount.sortByName();
                    break;
                case 8:
                    manageAccount.sortById();
                    break;
            }
        } while (choice != 0);
    }

    private static void menuAdminAboutProduct(Scanner scanner, ManageProduct manageProduct, ManageColor manageColor, ManageCart manageCart) {
        int choice = -1;
        do {
            System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
            System.out.println("| _ _ _ _ Menu Product Management _ _ _ _ |");
            System.out.println("|         1. Display all products         |");
            System.out.println("|         2. Create product               |");
            System.out.println("|         3. Update products              |");
            System.out.println("|         4. Delete products              |");
            System.out.println("|         5. Find products by name        |");
            System.out.println("|         6. Find products by id          |");
            System.out.println("|         7. Sort products by name        |");
            System.out.println("|         8. Menu setting color           |");
            System.out.println("|         9. Display cart(...)            |");
            System.out.println("|         0. Exit!                        |");
            System.out.println("| _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |");
            System.out.println("       --->Enter your choice here!<---     ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Please re-enter the number!");
            }

            switch (choice) {
                case 1:
                    manageProduct.displayAll();
                    break;
                case 2:
                    manageProduct.create();
                    break;
                case 3:
                    manageProduct.update();
                    break;
                case 4:
                    manageProduct.delete();
                    break;
                case 5:
                    manageProduct.searchByName();
                    break;
                case 6:
                    manageProduct.searchById();
                    break;
                case 7:
                    manageProduct.sortByName();
                    break;
                case 8:
                    menuColor(scanner, manageColor);
                    break;
                case 9:
                    manageCart.displayCart();
                    break;
            }
        } while (choice != 0);
    }

    private static void menuColor(Scanner scanner, ManageColor manageColor) {
        int choice = -1;
        do {
            System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
            System.out.println("|_ _ _ _ Menu Color Product_ _ _ _ _ _|");
            System.out.println("|       1. Display all color          |");
            System.out.println("|       2. Create color               |");
            System.out.println("|       3. Update color               |");
            System.out.println("|       4. Delete color               |");
            System.out.println("|       0. Exit!                      |");
            System.out.println("| _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |");

            System.out.println("-->Enter your choice here!<--");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Please re-enter the number!");
            }

            switch (choice) {
                case 1:
                    manageColor.displayAll();
                    break;
                case 2:
                    manageColor.create();
                    break;
                case 3:
                    manageColor.update();
                    break;
                case 4:
                    manageColor.delete();
                    break;
            }
        } while (choice != 0);
    }

    private static void menuUser(Scanner scanner, ManageAccount manageAccount, ManageProduct manageProduct, ManageCart manageCart) {
        int choice = -1;
        do {
            System.out.println("| _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |");
            System.out.println("| _ _ _ _ _ _ _ _ _ _Menu_ _ _ _ _ _ _ _ _|");
            System.out.println("|      1. View your information           |");
            System.out.println("|      2. Update your account             |");
            System.out.println("|      3. View all products               |");
            System.out.println("|      4. Search product by name          |");
            System.out.println("|      5. Search product by color         |");
            System.out.println("|      6. Search product by price         |");
            System.out.println("|      7. Sort by price (about)           |");
            System.out.println("|      8. Sort by name                    |");
            System.out.println("|      9. Sort by id                      |");
            System.out.println("|      10. Add to cart(...)               |");
            System.out.println("|      11. Display cart(...)              |");
            System.out.println("|      12. Delete product in cart(...)    |");
            System.out.println("|      13. Change quantity product(...)   |");
            System.out.println("|      14. Pay product in cart(...)       |");
            System.out.println("|      0. Exit!                           |");
            System.out.println("| _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ |");
            System.out.println("       -->Enter your choice here!<--   ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please re-enter the number!");
            }

            switch (choice) {
                case 1:
                    manageAccount.displayUser();
                    break;
                case 2:
                    manageAccount.updateUser();
                    break;
                case 3:
                    manageProduct.displayAll();
                    break;
                case 4:
                    manageProduct.searchByName();
                    break;
                case 5:
                    manageProduct.searchByColor();
                    break;
                case 6:
                    manageProduct.searchByPrice();
                    break;
                case 7:
                    manageProduct.sortByPriceType();
                    break;
                case 8:
                    manageProduct.sortByName();
                    break;
                case 9:
                    manageProduct.sortById();
                    break;
                case 10:
                    manageCart.cart();
                    break;
                case 11:
                    manageCart.displayCartUser();
                    break;
                case 12:
                    manageCart.deleteCart();
                    break;
                case 13:
                    manageCart.updateQuantity();
                    break;
                case 14:
                    manageCart.payCart();
                    break;
            }
        } while (choice != 0);
    }
}
