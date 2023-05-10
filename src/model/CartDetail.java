package model;

import java.io.Serializable;

public class CartDetail implements Serializable {
    private static final long serialVersionUID = 3L;
    public static int idUpCart;
    private int id;
    private Cart cart;
    private Product product;
    private int quantity;

    public CartDetail() {
    }

    public CartDetail(Cart idCart, Product product, int quantity) {
        this.id =++idUpCart;
        this.cart = idCart;
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id +"," + cart + "," + product + "," + quantity ;
    }
    public void display() {
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%s",
                this.id, this.cart.getName(), this.cart.getDate(),
                this.product.getId(), this.product.getName(), this.product.getColor().getNameColor(),
                this.product.getPrice()+" $", this.product.getDescription(),
                this.quantity + "\n");
    }
}