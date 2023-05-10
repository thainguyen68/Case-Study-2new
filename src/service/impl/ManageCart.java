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
    private final List<Cart> carts = new ArrayList<>();
    private final ArrayList<CartDetail> cartDetails;
    ManageProduct manageProduct;

    public ManageCart(ManageProduct manageProduct) {
        this.manageProduct = manageProduct;
        cartDetails = readBinary(PATH_FILE);
        checkDefaultIndex();
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
        int quantity = Integer.parseInt(scanner.nextLine());
        addCart(p, quantity, cart);
    }

    public void addCart(Product product, int quantity, Cart cart) {
        CartDetail cartDetail = new CartDetail(cart, product, quantity);
        cartDetails.add(cartDetail);
        writeBinary(PATH_FILE, cartDetails);
    }


    public CartDetail deleteCart() {
        displayCartUser();
        System.out.println("Enter id you want to delete product");
        int idCart =-1;
        try {
            idCart = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Have error, please try again!");
        }

        int index = 0;
        for (int i = 0; i < cartDetails.size(); i++) {
            if (cartDetails.get(i).getCart().getName().equals(String.valueOf(accountPresent.getUsername()))){
                if (idCart == cartDetails.get(i).getId()) {
                    i = index;
                    cartDetails.remove(cartDetails.get(i));
                    System.out.println("Removed from cart !");
                }
            }
            else {
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
            if (idCart == cartDetail.getId()) {
                return cartDetail;
            }
        }
        return null;
    }

    public void displayCart() {
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%s",
                "Id", "UserName", "Date-buy", "Id-Product", "Name-Product", "Color", "Price", "Description", "Quantity\n");
        for (CartDetail cartDetail : cartDetails) {
            cartDetail.display();
        }
    }
    public void sumPaid() {
        double sum = 0;
        for (CartDetail cartDetail : cartDetails) {
            sum += cartDetail.getProduct().getPrice() * cartDetail.getQuantity();
        }
        System.out.println("Total amount to be paid: " + sum);
    }

    public CartDetail displayCartUser() {
        String usName = String.valueOf(accountPresent.getUsername());
        double sum = 0;
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%s",
                "Id", "UserName", "Date-buy", "Id-Product", "Name-Product", "Color", "Price", "Description", "Quantity\n");
        for (int i = 0; i < cartDetails.size(); i++) {
            if (usName.equals(cartDetails.get(i).getCart().getName())) {
                cartDetails.get(i).display();
                sum += cartDetails.get(i).getProduct().getPrice() * cartDetails.get(i).getQuantity();
            }
        }
        System.out.println("Total amount to be paid: " + sum);
        return null;
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