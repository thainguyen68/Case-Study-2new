package service.impl;

import model.Cart;
import model.CartDetail;
import model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static controller.MainRun.accountPresent;

public class ManageCart {
    private final Scanner scanner = new Scanner(System.in);
    private final String PATH_FILE = "E:\\Case-Study-2\\src\\data\\Cart.txt";
    private final List<Cart> carts;
    private final ArrayList<CartDetail> cartDetails;
    ManageProduct manageProduct;

    public ManageCart(ManageProduct manageProduct) {
        this.manageProduct = manageProduct;
        cartDetails = readBinary(PATH_FILE);
        checkDefaultIndex();
        carts = new ArrayList<>();
    }

    private void checkDefaultIndex() {
        if (cartDetails.isEmpty()) {
            CartDetail.idUpCart = 0;
        } else {
            CartDetail.idUpCart = cartDetails.get(cartDetails.size() - 1).getId();
        }
    }

    public void cart() {
        String UserName = String.valueOf(accountPresent.getUsername());
        Cart cart = new Cart(UserName);
        for (Cart i : carts) {
            if (cart.getName().equals(UserName) && !i.isPaid()) {
                cart = i;
            }
        }
        manageProduct.displayAll();
        Product p = manageProduct.getById();
        System.out.println("Enter the quantity you want to buy");
        int quantity = 0;
        try {
            quantity = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Have error, please try again!");
        }
        addCart(p, quantity, cart);
    }

    public void addCart(Product product, int quantity, Cart cart) {
        CartDetail cartDetail = new CartDetail(cart, product, quantity);
        boolean flag = false;
        for (CartDetail c : cartDetails) {
            if (c.getProduct().getName().equals(product.getName()) && cart.getName().equals(c.getCart().getName()) && !c.getCart().isPaid()) {
                c.setQuantity(c.getQuantity() + quantity);
                flag = true;
            }
        }
        if (!flag) {
            cartDetails.add(cartDetail);
        }
        writeBinary(PATH_FILE, cartDetails);
    }

    public CartDetail deleteCart() {
        displayCartUser();
        System.out.println("Enter id you want to delete product");
        int idCart = -1;
        try {
            idCart = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Have error, please try again!");
        }

        int index = 0;
        for (int i = 0; i < cartDetails.size(); i++) {
            if (cartDetails.get(i).getCart().getName().equals(String.valueOf(accountPresent.getUsername()))) {
                if (idCart == cartDetails.get(i).getId()) {
                    i = index;
                    cartDetails.remove(cartDetails.get(i));
                    System.out.println("Removed from cart !");
                }
            } else {
                return null;
            }
        }
        writeBinary(PATH_FILE, cartDetails);
        return displayCartUser();
    }

    public CartDetail getById() {
        int idCart;
        do {
            try {
                System.out.println("Input id you want: ");
                idCart = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Have error, please try again!");
            }
        } while (true);
        for (CartDetail cartDetail : cartDetails) {
            if (idCart == cartDetail.getId() && cartDetail.getCart().getName().equals(accountPresent.getUsername())) {
                return cartDetail;
            }
        }
        return null;
    }

    public void displayCart() {
        double sum = 0;
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%s",
                "Id", "UserName", "Date-buy", "Id-Product", "Name-Product", "Color", "Price", "Description", "Quantity\n");
        for (CartDetail cartDetail : cartDetails) {
            if (cartDetail.getCart().isPaid() == true) {
                cartDetail.display();
                sum += cartDetail.getProduct().getPrice() * cartDetail.getQuantity();
            }
        }
        System.out.println("Total revenue: " + sum +" $");
    }

    public void sumPaid() {
        double sum = 0;
        for (CartDetail cartDetail : cartDetails) {
            if (!cartDetail.getCart().isPaid() && cartDetail.getCart().getName().equals(accountPresent.getUsername())) {
                sum += cartDetail.getProduct().getPrice() * cartDetail.getQuantity();
            }
        }
        System.out.println("Total amount to be paid: " + sum);
    }

    public CartDetail displayCartUser() {
        String usName = String.valueOf(accountPresent.getUsername());
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%s",
                "Id", "UserName", "Date-buy", "Id-Product", "Name-Product", "Color", "Price", "Description", "Quantity\n");
        for (int i = 0; i < cartDetails.size(); i++) {
            if (usName.equals(cartDetails.get(i).getCart().getName()) && !cartDetails.get(i).getCart().isPaid()) {
                cartDetails.get(i).display();
            }
        }
        sumPaid();
        return null;
    }

    public void payCart() {
        System.out.println("Do you want to pay for these products ?");
        System.out.println("y: yes");
        System.out.println("n: no");
        String choose = scanner.nextLine();
        if (choose.equals("y")) {
            for (CartDetail cartDetail : cartDetails) {
                if (cartDetail.getCart().getName().equals(accountPresent.getUsername())) {
                    cartDetail.getCart().setPaid(true);
                    System.out.println("you have successfully paid !");
                    writeBinary(PATH_FILE, cartDetails);
                }
            }
        } else if (choose.equals("n")) {
            System.out.println("you have not pay yet !!!");
        } else {
            System.out.println("no choice here !");
        }

    }


    public static void writeBinary(String path, ArrayList<CartDetail> cartDetails) {
        try {
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(cartDetails);
            output.close();
            file.close();
        } catch (IOException e) {
            System.err.println("Error!!!");
        }
    }

    public ArrayList<CartDetail> readBinary(String path) {
        File file = new File(path);
        ArrayList<CartDetail> cartDetails = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            cartDetails = (ArrayList<CartDetail>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cartDetails;
    }
}