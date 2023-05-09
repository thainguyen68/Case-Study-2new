package model;

public class Product {
    private static final long serialVersionUID = 3L;
    public static int idUp;
    private int id;
    private String name;
    private Color color;
    private double price;
    private String description;

    public Product() {
    }

    public Product(int id, String name, Color color, double price, String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.description = description;
    }
    public Product(String name, Color color, double price, String description) {
        this.id = ++idUp;
        this.name = name;
        this.color = color;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public String toString() {
        return
                id + "," + name + "," + color.getId() + "," + price + "," + description;
    }

    public void display() {
        System.out.printf("%-15s%-17s%-20s%-20s%s",
                this.id, this.name, this.color.getNameColor(), this.price+ " USD", this.description + "\n");
    }
}

