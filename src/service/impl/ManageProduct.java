package service.impl;

import ioFile.IOFile;
import model.Color;
import model.Product;
import service.Manage;
import service.ManageFind;
import service.ManageSort;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ManageProduct implements Manage<Product>, IOFile<Product>, ManageFind, ManageSort {
    private Scanner scanner;
    private List<Product> productList = new ArrayList<>();
    private final ManageColor manageColor;

    private final String PATH_FILE = "E:\\Case-Study-2\\src\\data\\Product.txt";

    public ManageProduct(Scanner scanner, ManageColor manageColor) {
        this.scanner = scanner;
        this.manageColor = manageColor;
//        addProduct();
        productList = read(PATH_FILE);
        checkDefaultIndex();
    }

    public void addProduct() {
        Product product1 = new Product(1, "ip12", new Color(1, "red"), 5.5, "new");
        Product product2 = new Product(2, "ip13", new Color(2, "blue"), 6.1, "light-new");
        Product product3 = new Product(3, "ip14", new Color(3, "white"), 7.2, "80%");
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        write(productList, PATH_FILE);
    }

    private void checkDefaultIndex() {
        if (productList.isEmpty()) {
            Product.idUp = 0;
        } else {
            Product.idUp = productList.get(productList.size() - 1).getId();
        }
    }

    public Product create() {
        System.out.println("Add new products !");
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();
        System.out.println("Enter product color:");
        Color color = getColor();
        System.out.println("Enter product price:");
        Double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter product description:");
        String description = scanner.nextLine();
        Product productNew = new Product(name, color, price, description);
        productList.add(productNew);
        write(productList, PATH_FILE);
        return productNew;
    }

    private Color getColor() {
        manageColor.displayAll();
        System.out.println("Input color of product: ");
        Color color;
        do {
            int id = manageColor.inputId();
            color = manageColor.getById(id);
        } while (color == null);
        return color;
    }

    public Product update() {
        Product product = getById();
        if (product != null) {
            System.out.println("Change product name: ");
            String name = scanner.nextLine();
            if (!name.equals("")) {
                product.setName(name);
            }

            System.out.println("Change product color: ");
            product.setColor(getColor());

            System.out.println("Change product price: ");
            double price;
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price > 0) {
                    product.setPrice(price);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please re-enter the number!");
            }

            System.out.println("Change product description: ");
            String description = scanner.nextLine();
            if (!description.equals("")) {
                product.setDescription(description);
            }
        }
        write(productList, PATH_FILE);
        return product;
    }

    public Product delete() {
        Product product = getById();
        if (product != null) {
            productList.remove(product);
        }
        write(productList, PATH_FILE);
        return product;
    }

    public Product getById() {
        int id;
        do {
            try {
                System.out.println("Input id you want: ");
                id = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Have error, please try again!");
            }
        } while (true);
        boolean checkIdPro = false;
        do {
            for (Product product : productList) {
                if (product.getId() == id) {
                    checkIdPro = true;
                    return product;
                }
            }
            if (!checkIdPro) {
                System.out.println("No product in store");
                return getById();
            }
        } while (true);
    }

    public void displayAll() {
        System.out.printf("%-15s%-17s%-20s%-20s%s",
                "Id", "Name", "Color", "Price", "Description\n");
        for (Product p : productList) {
            p.display();
        }
    }

    public void write(List<Product> productList, String path) {
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Product p : productList) {
                bufferedWriter.write(p.toString() + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

    }

    public List<Product> read(String path) {
        File file = new File(path);
        List<Product> productList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                String[] strings = data.split(",");
                Color color = manageColor.getById(Integer.parseInt(strings[2]));
                Product product = new Product(Integer.parseInt(strings[0]), strings[1], color, Double.parseDouble(strings[3]), strings[4]);
                productList.add(product);
            }
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
        return productList;
    }

    public void searchById() {
        System.out.println("Enter id of products you want to find: ");
        int idSearch = -1;
        try {
            idSearch = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Please re-enter the number!");
        }
        boolean check = false;
        for (int i = 0; i < productList.size(); i++) {
            if (idSearch == productList.get(i).getId()) {
                check = true;
                System.out.printf("%-15s%-15s%-15s%-18s%s",
                        "Id", "Name", "Color", "Price", "Description\n");
                productList.get(i).display();
            }
        }
        if (!check) {
            System.err.println("not found");
        }
    }

    public void searchByName() {
        System.out.println("Enter name product you want to find");
        String name = scanner.nextLine();
        System.out.printf("%-15s%-15s%-15s%-18s%s",
                "Id", "Name", "Color", "Price", "Description\n");
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                product.display();
            }
        }
    }

    public void searchByColor() {
        System.out.println("Enter color product you want to find");
        String name = scanner.nextLine();
        System.out.printf("%-15s%-15s%-15s%-18s%s",
                "Id", "Name", "Color", "Price", "Description\n");
        for (Product product : productList) {
            if (product.getColor().getNameColor().equalsIgnoreCase(name)) {
                product.display();
            }
        }
    }

    public void searchByPrice() {
        System.out.println("Enter about price you want to find");
        System.out.println("Enter price from: ");
        int priceMin = -1;
        try {
            priceMin = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Please re-enter the number!");
        }
        System.out.println("Enter final price: ");
        int priceMax = -1;
        try {
            priceMax = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Please re-enter the number!");
        }
        System.out.printf("%-15s%-15s%-15s%-18s%s",
                "Id", "Name", "Color", "Price", "Description\n");
        for (Product product : productList) {
            if (priceMin <= product.getPrice() && product.getPrice() <= priceMax) {
                product.display();
            }
        }
    }

    public void sortByPriceType() {
        System.out.println("How would you like to arrange?");
        System.out.println("1. Low to high ");
        System.out.println("2. High to low ");
        int chooseSort = -1;
        try {
            chooseSort = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Please re-enter the number!");
        }
        if (chooseSort == 1) {
            sortByPriceLowToHigh();
        } else if (chooseSort == 2) {
            sortByPriceHighTolLow();
        } else {
            System.out.println("no choice here !");
        }
    }

    public void sortByPriceLowToHigh() {
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (o1.getPrice() > o2.getPrice()) return 1;
                else if (o1.getPrice() < o2.getPrice()) return -1;
                else return 0;
            }
        });
        displayAll();
    }

    public void sortByPriceHighTolLow() {
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (o1.getPrice() > o2.getPrice()) return -1;
                else if (o1.getPrice() < o2.getPrice()) return 1;
                else return 0;
            }
        });
        displayAll();
    }


    public void sortByName() {
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        displayAll();
    }

    public void sortById() {
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getId() - o1.getId();
            }
        });
        displayAll();
    }
}
